using worker;
using worker.src.model;
using worker.src.service;

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
            Host = config["Worker:Smtp:Host"] ?? "localhost",
            Port = Int32.Parse(config["Worker:Smtp:Port"]?.ToString() ?? "25"),
            UseDefaultCredentials = false,
            Credentials = new NetworkCredential(config["Worker:Smtp:Username"], config["Worker:Smtp:Password"]),
            EnableSsl = true
        };

        services
            .AddSingleton<ISender>(s => new SmtpSender(client))
            .AddHostedService<MailWorker>()
            .AddTransient<IMailService, MailService>()
            .AddDbContext<FmiMaterialsContext>()
            .AddFluentEmail(config["Worker:Sender"])
            .AddRazorRenderer();
    })
    .Build();

host.Run();
