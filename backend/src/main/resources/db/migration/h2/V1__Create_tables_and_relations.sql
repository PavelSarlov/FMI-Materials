CREATE TABLE
  faculty_departments(
    id IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT PK_faculty_departments PRIMARY KEY(id)
  );

CREATE TABLE
  courses(
    id IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    faculty_department_id INT NOT NULL,
    course_group INT NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    CONSTRAINT PK_courses PRIMARY KEY(id),
    CONSTRAINT FK_courses__faculty_department FOREIGN KEY(faculty_department_id) REFERENCES faculty_departments(id) ON DELETE SET NULL
  );

CREATE TABLE
  sections (
    id IDENTITY,
    name VARCHAR(50) NOT NULL,
    course_id INT NOT NULL,
    CONSTRAINT PK_sections PRIMARY KEY(id),
    CONSTRAINT FK_sections__courses FOREIGN KEY(course_id) REFERENCES courses(id) ON DELETE CASCADE
  );

CREATE TABLE
  materials (
    id IDENTITY,
    file_format VARCHAR(255) NOT NULL,
    file_name VARCHAR(100) NOT NULL,
    data BYTEA NOT NULL,
    section_id INT NOT NULL,
    CONSTRAINT PK_materials PRIMARY KEY(id),
    CONSTRAINT FK_materials__sections FOREIGN KEY(section_id) REFERENCES sections(id) ON DELETE CASCADE,
    UNIQUE (section_id, file_name)
  );

CREATE TABLE
  user_roles(
    id IDENTITY,
    name VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT PK_user_roles PRIMARY KEY(id)
  );

CREATE TABLE
  users(
    id IDENTITY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(60) NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY(id)
  );

CREATE TABLE
  users_user_roles(
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT PK_users_user_roles PRIMARY KEY(role_id, user_id),
    CONSTRAINT FK_users_user_roles__users FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK_users_user_roles__user_roles FOREIGN KEY(role_id) REFERENCES user_roles(id)
  );

CREATE TABLE
  user_favourite_courses(
    course_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT PK_user_favourite_courses PRIMARY KEY(course_id, user_id),
    CONSTRAINT FK_user_favourite_courses__users FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK_user_favourite_courses__courses FOREIGN KEY(course_id) REFERENCES courses(id)
  );

CREATE TABLE
  user_courses_lists(
    id IDENTITY,
    list_name VARCHAR(50) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    CONSTRAINT PK_user_courses_lists PRIMARY KEY(id),
    CONSTRAINT FK_user_courses_lists__users FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
  );

CREATE TABLE
  courses_user_courses_lists(
    course_id INT NOT NULL,
    user_courses_list_id INT NOT NULL,
    CONSTRAINT PK_courses_user_courses_lists PRIMARY KEY(course_id, user_courses_list_id),
    CONSTRAINT FK_courses_user_courses_lists__courses FOREIGN KEY(course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT FK_courses_user_courses_lists__user_courses_lists FOREIGN KEY(user_courses_list_id) REFERENCES user_courses_lists(id) ON DELETE CASCADE
  );

CREATE TABLE
  material_requests (
    id IDENTITY,
    file_format VARCHAR(255) NOT NULL,
    file_name VARCHAR(100) NOT NULL,
    data BYTEA NOT NULL,
    user_id INT NOT NULL,
    section_id INT NOT NULL,
    CONSTRAINT PK_material_requests PRIMARY KEY(id),
    CONSTRAINT FK_material_requests__users FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK_material_requests__sections FOREIGN KEY(section_id) REFERENCES sections(id) ON DELETE CASCADE,
    UNIQUE (section_id, file_name)
  );
