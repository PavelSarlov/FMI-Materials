using System;
using System.Collections.Generic;

namespace mailer;

public partial class Section
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public int CourseId { get; set; }

    public virtual Course Course { get; set; } = null!;

    public virtual ICollection<MaterialRequest> MaterialRequests { get; } = new List<MaterialRequest>();

    public virtual ICollection<Material> Materials { get; } = new List<Material>();
}
