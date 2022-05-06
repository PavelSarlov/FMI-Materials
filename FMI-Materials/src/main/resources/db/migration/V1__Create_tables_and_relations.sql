CREATE TABLE courses(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    faculty_department INT NOT NULL,
    course_group INT NOT NULL,

    CONSTRAINT PK_courses PRIMARY KEY(id)
);

CREATE TABLE sections (
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    course_id INT NOT NULL,

    CONSTRAINT PK_sections PRIMARY KEY(id),
    CONSTRAINT FK_sections__courses FOREIGN KEY(id) REFERENCES courses(id) ON DELETE NO ACTION
);

CREATE TABLE materials (
    id SERIAL,
    file_format VARCHAR(50) NOT NULL,
    data BYTEA NOT NULL,
    section_id INT NOT NULL,

    CONSTRAINT PK_materials PRIMARY KEY(id),
    CONSTRAINT FK_materials__sections FOREIGN KEY(id) REFERENCES sections(id) ON DELETE NO ACTION
);

CREATE TABLE users(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT PK_users PRIMARY KEY(id)
);

CREATE TABLE user_courses_lists(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,

    CONSTRAINT PK_user_courses_lists PRIMARY KEY(Id),
    CONSTRAINT FK_user_courses_lists__users FOREIGN KEY(Id) REFERENCES Users(Id) ON DELETE NO ACTION
);

CREATE TABLE courses__user_courses_lists(
    course_id INT NOT NULL,
    user_courses_list_id INT NOT NULL,

    CONSTRAINT PK_courses__user_courses_lists PRIMARY KEY(course_id, user_courses_list_id),
    CONSTRAINT FK_courses FOREIGN KEY(course_id) REFERENCES courses(id) ON DELETE NO ACTION,
    CONSTRAINT FK_user_courses_lists FOREIGN KEY(user_courses_list_id) REFERENCES user_courses_lists(id) ON DELETE NO ACTION
);
