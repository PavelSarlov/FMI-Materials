using mailer;

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
        services
            .AddHostedService<MailWorker>()
            .AddDbContext<FmiMaterialsContext>();
    })
    .Build();

host.Run();
