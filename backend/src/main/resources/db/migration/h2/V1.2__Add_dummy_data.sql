INSERT INTO courses(name, description, faculty_department_id, course_group, created_by)
VALUES ('Web Development with Java', 'WDWJ, Spring Boot, MVC', 13, 1, 'Admin Adminchev');

INSERT INTO sections(name, course_id)
VALUES ('Home', 1);

INSERT INTO users(id, name, email, password_hash)
VALUES (1, 'U1', 'u1@email.com', 'pass');

INSERT INTO user_favourite_courses(course_id, user_id)
VALUES (1, 1);

INSERT INTO users_user_roles(role_id, user_id)
VALUES (1, 1);
