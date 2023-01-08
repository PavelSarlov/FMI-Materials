using System;
using System.Collections.Generic;

namespace worker.src.model;

public partial class WorkerJob
{
    public int Id { get; set; }

    public string Type { get; set; } = null!;

    public string? Data { get; set; }

    public int? Status { get; set; }
}
