using worker.src.service;
using worker.src.model;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using worker.src.vo;

namespace worker;

public class MailWorker : BackgroundService
{
    private readonly ILogger<MailWorker> _logger;
    private readonly IConfiguration _config;
    private readonly IMailService _mailService;

    private readonly int _MAX_TRIES = 5;

    public MailWorker(ILogger<MailWorker> logger, IConfiguration config, IMailService mailService)
    {
        _logger = logger;
        _config = config;
        _mailService = mailService;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        while (!stoppingToken.IsCancellationRequested)
        {
            await using var ctx = FmiMaterialsContext.New(_config);
            await ctx.Database.EnsureCreatedAsync();

            var jobs = ctx.WorkerJobs
                .Where(job => job.Type == "email" && job.Status == (int)src.vo.WorkerJobStatus.Pending)
                .Take(100);

            foreach (var job in jobs)
            {
                int currentTry = 0;

                while (currentTry++ < _MAX_TRIES)
                {
                    try
                    {
                        JObject data = JsonConvert.DeserializeObject<dynamic>(job.Data ?? "") ?? new JObject();

                        bool success = await _mailService.SendMail((string)data["to"]!, (string)data["subject"]!, (string)data["type"]!, data);

                        if (success)
                        {
                            job.Status = (int)WorkerJobStatus.Completed;
                            break;
                        }
                    }
                    catch (Exception e)
                    {
                        _logger.LogError(e.Message);
                        _logger.LogError(e.StackTrace);
                    }
                }

                if (job.Status != (int)WorkerJobStatus.Completed)
                {
                    job.Status = (int)WorkerJobStatus.Failed;
                }
            }

            await ctx.SaveChangesAsync();

            await Task.Delay(3000, stoppingToken);
        }
    }
}
