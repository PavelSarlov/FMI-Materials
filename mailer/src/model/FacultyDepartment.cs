using System;
using System.Collections.Generic;

namespace mailer;

public partial class FacultyDepartment
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Course> Courses { get; } = new List<Course>();
}
