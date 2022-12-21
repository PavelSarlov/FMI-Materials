using System;
using System.Collections.Generic;

namespace mailer.src.model;

public partial class User
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public string Email { get; set; } = null!;

    public string PasswordHash { get; set; } = null!;

    public virtual ICollection<MaterialRequest> MaterialRequests { get; } = new List<MaterialRequest>();

    public virtual ICollection<UserCoursesList> UserCoursesLists { get; } = new List<UserCoursesList>();

    public virtual ICollection<Course> Courses { get; } = new List<Course>();

    public virtual ICollection<UserRole> Roles { get; } = new List<UserRole>();
}
