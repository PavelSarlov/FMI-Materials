INSERT INTO users(name, password_hash, email)
VALUES ('John Doe', crypt('johndoe', gen_salt('bf', 10))::VARCHAR(60), 'john@doe.com');

INSERT INTO courses(name, description, faculty_department_id, course_group, created_by)
VALUES ('Web Development with Java', 'WDWJ, Spring Boot, MVC', 13, 1, 'Admin Adminchev');

INSERT INTO sections(name, course_id)
VALUES ('Home', 1);
