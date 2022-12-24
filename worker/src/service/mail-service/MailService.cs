using FluentEmail.Core;

namespace worker.src.service;

public class MailService : IMailService
{
    private readonly String _templateDir = Path.Combine(Directory.GetCurrentDirectory(), "src", "template");
    private readonly IFluentEmailFactory _femail;
    private readonly ILogger<MailService> _logger;
    private readonly IConfiguration _config;

    public MailService(IFluentEmailFactory femail, ILogger<MailService> logger, IConfiguration config)
    {
        _femail = femail;
        _logger = logger;
        _config = config;
    }

    public async Task<bool> SendMail<T>(string to, string subject, string bodyTemplate, T data)
    {
        String type = System.IO.File.Exists(Path.Combine(_templateDir, "html", bodyTemplate + ".cshtml")) ? "html" : "plain";
        String filePath = Path.Combine(_templateDir, type, bodyTemplate + ".cshtml");

        var email = await _femail.Create()
            .To(to)
            .Subject(subject)
            .UsingTemplateFromFile(filePath, new { data })
            .SendAsync(null);

        if (email.Successful)
        {
            _logger.LogInformation("Email send successfully to " + to);
            return true;
        }
        else
        {
            _logger.LogError(string.Join("\n", email.ErrorMessages));
            return false;
        }
    }
}
