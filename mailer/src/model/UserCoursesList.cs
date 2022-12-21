using System;
using System.Collections.Generic;

namespace mailer.src.model;

public partial class UserCoursesList
{
    public int Id { get; set; }

    public string ListName { get; set; } = null!;

    public int UserId { get; set; }

    public virtual User User { get; set; } = null!;

    public virtual ICollection<Course> Courses { get; } = new List<Course>();
}
