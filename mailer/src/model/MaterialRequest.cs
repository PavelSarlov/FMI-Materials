using System;
using System.Collections.Generic;

namespace mailer;

public partial class MaterialRequest
{
    public int Id { get; set; }

    public string FileFormat { get; set; } = null!;

    public string FileName { get; set; } = null!;

    public byte[] Data { get; set; } = null!;

    public int UserId { get; set; }

    public int SectionId { get; set; }

    public virtual Section Section { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
