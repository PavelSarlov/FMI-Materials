INSERT INTO users(id, name, password, email)
VALUES (1, 'John Doe', sha256('johndoe')::VARCHAR, 'john@doe');

INSERT INTO courses(id, name, description, faculty_department, course_group)
VALUES (1, 'Web Development with Java', 'WDWJ, Spring Boot, MVC', 1, 1);

INSERT INTO sections(id, name, course_id)
VALUES (1, 'Home', 1);
