using Microsoft.EntityFrameworkCore;

namespace worker.src.model;

public partial class FmiMaterialsContext : DbContext
{
    public IConfiguration Config { get; set; }

    public FmiMaterialsContext()
    {
    }

    public FmiMaterialsContext(DbContextOptions<FmiMaterialsContext> options)
        : base(options)
    {
    }

    public static FmiMaterialsContext New(IConfiguration config)
    {
        var ctx = new FmiMaterialsContext();
        ctx.Config = config;
        return ctx;
    }

    public virtual DbSet<Course> Courses { get; set; }

    public virtual DbSet<FacultyDepartment> FacultyDepartments { get; set; }

    public virtual DbSet<FlywaySchemaHistory> FlywaySchemaHistories { get; set; }

    public virtual DbSet<Material> Materials { get; set; }

    public virtual DbSet<MaterialRequest> MaterialRequests { get; set; }

    public virtual DbSet<Section> Sections { get; set; }

    public virtual DbSet<User> Users { get; set; }

    public virtual DbSet<UserCoursesList> UserCoursesLists { get; set; }

    public virtual DbSet<UserRole> UserRoles { get; set; }

    public virtual DbSet<WorkerJob> WorkerJobs { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        => optionsBuilder.UseNpgsql($"Host={Config["Database:Host"]};Port={Config["Database:Port"]};Username={Config["Database:Username"]};Password={Config["Database:Password"]};Database={Config["Database:Database"]}");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.HasPostgresExtension("pgcrypto");

        modelBuilder.Entity<Course>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_courses");

            entity.ToTable("courses");

            entity.HasIndex(e => e.Name, "courses_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.CourseGroup).HasColumnName("course_group");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(50)
                .HasColumnName("created_by");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .HasColumnName("description");
            entity.Property(e => e.FacultyDepartmentId).HasColumnName("faculty_department_id");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .HasColumnName("name");

            entity.HasOne(d => d.FacultyDepartment).WithMany(p => p.Courses)
                .HasForeignKey(d => d.FacultyDepartmentId)
                .OnDelete(DeleteBehavior.SetNull)
                .HasConstraintName("fk_courses__faculty_department");

            entity.HasMany(d => d.UserCoursesLists).WithMany(p => p.Courses)
                .UsingEntity<Dictionary<string, object>>(
                    "CoursesUserCoursesList",
                    r => r.HasOne<UserCoursesList>().WithMany()
                        .HasForeignKey("UserCoursesListId")
                        .HasConstraintName("fk_courses_user_courses_lists__user_courses_lists"),
                    l => l.HasOne<Course>().WithMany()
                        .HasForeignKey("CourseId")
                        .HasConstraintName("fk_courses_user_courses_lists__courses"),
                    j =>
                    {
                        j.HasKey("CourseId", "UserCoursesListId").HasName("pk_courses_user_courses_lists");
                        j.ToTable("courses_user_courses_lists");
                    });

            entity.HasMany(d => d.Users).WithMany(p => p.Courses)
                .UsingEntity<Dictionary<string, object>>(
                    "UserFavouriteCourse",
                    r => r.HasOne<User>().WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("fk_user_favourite_courses__users"),
                    l => l.HasOne<Course>().WithMany()
                        .HasForeignKey("CourseId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("fk_user_favourite_courses__courses"),
                    j =>
                    {
                        j.HasKey("CourseId", "UserId").HasName("pk_user_favourite_courses");
                        j.ToTable("user_favourite_courses");
                    });
        });

        modelBuilder.Entity<FacultyDepartment>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_faculty_departments");

            entity.ToTable("faculty_departments");

            entity.HasIndex(e => e.Name, "faculty_departments_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .HasColumnName("name");
        });

        modelBuilder.Entity<FlywaySchemaHistory>(entity =>
        {
            entity.HasKey(e => e.InstalledRank).HasName("flyway_schema_history_pk");

            entity.ToTable("flyway_schema_history");

            entity.HasIndex(e => e.Success, "flyway_schema_history_s_idx");

            entity.Property(e => e.InstalledRank)
                .ValueGeneratedNever()
                .HasColumnName("installed_rank");
            entity.Property(e => e.Checksum).HasColumnName("checksum");
            entity.Property(e => e.Description)
                .HasMaxLength(200)
                .HasColumnName("description");
            entity.Property(e => e.ExecutionTime).HasColumnName("execution_time");
            entity.Property(e => e.InstalledBy)
                .HasMaxLength(100)
                .HasColumnName("installed_by");
            entity.Property(e => e.InstalledOn)
                .HasDefaultValueSql("now()")
                .HasColumnType("timestamp without time zone")
                .HasColumnName("installed_on");
            entity.Property(e => e.Script)
                .HasMaxLength(1000)
                .HasColumnName("script");
            entity.Property(e => e.Success).HasColumnName("success");
            entity.Property(e => e.Type)
                .HasMaxLength(20)
                .HasColumnName("type");
            entity.Property(e => e.Version)
                .HasMaxLength(50)
                .HasColumnName("version");
        });

        modelBuilder.Entity<Material>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_materials");

            entity.ToTable("materials");

            entity.HasIndex(e => new { e.SectionId, e.FileName }, "materials_section_id_file_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Data).HasColumnName("data");
            entity.Property(e => e.FileFormat)
                .HasMaxLength(255)
                .HasColumnName("file_format");
            entity.Property(e => e.FileName)
                .HasMaxLength(100)
                .HasColumnName("file_name");
            entity.Property(e => e.SectionId).HasColumnName("section_id");

            entity.HasOne(d => d.Section).WithMany(p => p.Materials)
                .HasForeignKey(d => d.SectionId)
                .HasConstraintName("fk_materials__sections");
        });

        modelBuilder.Entity<MaterialRequest>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_material_requests");

            entity.ToTable("material_requests");

            entity.HasIndex(e => new { e.SectionId, e.FileName }, "material_requests_section_id_file_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Data).HasColumnName("data");
            entity.Property(e => e.FileFormat)
                .HasMaxLength(255)
                .HasColumnName("file_format");
            entity.Property(e => e.FileName)
                .HasMaxLength(100)
                .HasColumnName("file_name");
            entity.Property(e => e.SectionId).HasColumnName("section_id");
            entity.Property(e => e.UserId).HasColumnName("user_id");

            entity.HasOne(d => d.Section).WithMany(p => p.MaterialRequests)
                .HasForeignKey(d => d.SectionId)
                .HasConstraintName("fk_material_requests__sections");

            entity.HasOne(d => d.User).WithMany(p => p.MaterialRequests)
                .HasForeignKey(d => d.UserId)
                .HasConstraintName("fk_material_requests__users");
        });

        modelBuilder.Entity<Section>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_sections");

            entity.ToTable("sections");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.CourseId).HasColumnName("course_id");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .HasColumnName("name");

            entity.HasOne(d => d.Course).WithMany(p => p.Sections)
                .HasForeignKey(d => d.CourseId)
                .HasConstraintName("fk_sections__courses");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_users");

            entity.ToTable("users");

            entity.HasIndex(e => e.Email, "users_email_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Email)
                .HasMaxLength(50)
                .HasColumnName("email");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .HasColumnName("name");
            entity.Property(e => e.PasswordHash)
                .HasMaxLength(60)
                .HasColumnName("password_hash");
        });

        modelBuilder.Entity<UserCoursesList>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_user_courses_lists");

            entity.ToTable("user_courses_lists");

            entity.HasIndex(e => e.ListName, "user_courses_lists_list_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.ListName)
                .HasMaxLength(50)
                .HasColumnName("list_name");
            entity.Property(e => e.UserId).HasColumnName("user_id");

            entity.HasOne(d => d.User).WithMany(p => p.UserCoursesLists)
                .HasForeignKey(d => d.UserId)
                .HasConstraintName("fk_user_courses_lists__users");
        });

        modelBuilder.Entity<UserRole>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_user_roles");

            entity.ToTable("user_roles");

            entity.HasIndex(e => e.Name, "user_roles_name_key").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Name)
                .HasMaxLength(20)
                .HasColumnName("name");

            entity.HasMany(d => d.Users).WithMany(p => p.Roles)
                .UsingEntity<Dictionary<string, object>>(
                    "UsersUserRole",
                    r => r.HasOne<User>().WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("fk_users_user_roles__users"),
                    l => l.HasOne<UserRole>().WithMany()
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("fk_users_user_roles__user_roles"),
                    j =>
                    {
                        j.HasKey("RoleId", "UserId").HasName("pk_users_user_roles");
                        j.ToTable("users_user_roles");
                    });
        });

        modelBuilder.Entity<WorkerJob>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("pk_worker_jobs");

            entity.ToTable("worker_jobs");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Data)
                .HasColumnType("jsonb")
                .HasColumnName("data");
            entity.Property(e => e.Type)
                .HasColumnType("character varying")
                .HasColumnName("type");
            entity.Property(e => e.Status)
                .HasColumnType("integer")
                .HasColumnName("status");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
