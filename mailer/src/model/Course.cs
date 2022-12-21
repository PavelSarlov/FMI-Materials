using System;
using System.Collections.Generic;

namespace mailer.src.model;

public partial class Course
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public string Description { get; set; } = null!;

    public int FacultyDepartmentId { get; set; }

    public int CourseGroup { get; set; }

    public string CreatedBy { get; set; } = null!;

    public virtual FacultyDepartment FacultyDepartment { get; set; } = null!;

    public virtual ICollection<Section> Sections { get; } = new List<Section>();

    public virtual ICollection<UserCoursesList> UserCoursesLists { get; } = new List<UserCoursesList>();

    public virtual ICollection<User> Users { get; } = new List<User>();
}
