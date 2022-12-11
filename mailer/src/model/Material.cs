using System;
using System.Collections.Generic;

namespace mailer;

public partial class Material
{
    public int Id { get; set; }

    public string FileFormat { get; set; } = null!;

    public string FileName { get; set; } = null!;

    public byte[] Data { get; set; } = null!;

    public int SectionId { get; set; }

    public virtual Section Section { get; set; } = null!;
}
