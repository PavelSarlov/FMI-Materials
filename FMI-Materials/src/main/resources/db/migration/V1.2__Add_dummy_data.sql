INSERT INTO users(id, name, password_hash, email)
VALUES (2, 'John Doe', crypt('johndoe', gen_salt('bf', 10))::VARCHAR(60), 'john@doe.com');

INSERT INTO courses(id, name, description, faculty_department_id, course_group, created_by)
VALUES (1, 'Web Development with Java', 'WDWJ, Spring Boot, MVC', 13, 1, 'Admin Adminchev');

INSERT INTO sections(id, name, course_id)
VALUES (1, 'Home', 1);
