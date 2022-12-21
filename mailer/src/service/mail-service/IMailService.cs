namespace mailer.src.service;

public interface IMailService
{
    public Task<bool> SendMail<T>(string to, string subject, string bodyTemplate, T data);
}
