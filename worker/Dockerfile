FROM mcr.microsoft.com/dotnet/aspnet:6.0
FROM mcr.microsoft.com/dotnet/sdk:6.0

RUN mkdir -p /app/worker

WORKDIR /app/worker

COPY . .

RUN mv appsettings.docker.json appsettings.json

CMD ["dotnet", "run"]
