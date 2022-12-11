using System;
using System.Collections.Generic;

namespace mailer;

public partial class UserRole
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<User> Users { get; } = new List<User>();
}
