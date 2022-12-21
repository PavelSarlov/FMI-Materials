using mailer;
using mailer.src.model;
using mailer.src.service;

using System.Net;
using System.Net.Mail;

using FluentEmail.Core.Interfaces;
using FluentEmail.Smtp;

IHost host = Host.CreateDefaultBuilder(args)
    .ConfigureHostConfiguration(config =>
    {
        config
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", false)
            .AddJsonFile("appsettings-local.json", true)
            .AddEnvironmentVariables();
    })
    .ConfigureServices((hostContext, services) =>
    {
        var config = hostContext.Configuration;

        var client = new SmtpClient
        {
            Host = config["Mailer:Smtp:Host"] ?? "localhost",
            Port = Int32.Parse(config["Mailer:Smtp:Port"]?.ToString() ?? "25"),
            UseDefaultCredentials = false,
            Credentials = new NetworkCredential(config["Mailer:Smtp:Username"], config["Mailer:Smtp:Password"]),
            EnableSsl = true
        };

        services
            .AddSingleton<ISender>(s => new SmtpSender(client))
            .AddHostedService<MailWorker>()
            .AddTransient<IMailService, MailService>()
            .AddDbContext<FmiMaterialsContext>()
            .AddFluentEmail(config["Mailer:Sender"])
            .AddRazorRenderer();
    })
    .Build();

host.Run();
