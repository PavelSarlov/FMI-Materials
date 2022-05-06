INSERT INTO Users(Id, Name, Password, Email)
VALUES (1, 'John Doe', sha256('johndoe')::VARCHAR, 'john@doe');

INSERT INTO Courses(Id, Name, Description, FacultyDepartment, CourseGroup)
VALUES (1, 'Web Development with Java', 'WDWJ, Spring Boot, MVC', 1, 1);

INSERT INTO Sections(Id, Name, CourseId)
VALUES (1, 'Home', 1);
