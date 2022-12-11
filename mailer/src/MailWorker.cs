namespace mailer;

public class MailWorker : BackgroundService
{
    private readonly ILogger<MailWorker> _logger;
    private readonly IConfiguration _config;

    public MailWorker(ILogger<MailWorker> logger, DatabaseConfig dbConfig, IConfiguration config)
    {
        _logger = logger;
        _config = config;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        while (!stoppingToken.IsCancellationRequested)
        {
            await using var ctx = FmiMaterialsContext.New(_config);
            await ctx.Database.EnsureCreatedAsync();

            var user = ctx.Users.Find(1);

            _logger.LogInformation(user?.Email);

            await Task.Delay(3000, stoppingToken);
        }
    }
}
