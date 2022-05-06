CREATE TABLE Courses(
    Id SERIAL,
    Name VARCHAR(50) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    FacultyDepartment INT NOT NULL,
    CourseGroup INT NOT NULL,

    CONSTRAINT PK_Courses PRIMARY KEY(Id)
);

CREATE TABLE Sections (
    Id SERIAL,
    Name VARCHAR(50) NOT NULL,
    CourseId INT NOT NULL,

    CONSTRAINT PK_Sections PRIMARY KEY(Id),
    CONSTRAINT FK_Sections_Courses FOREIGN KEY(Id) REFERENCES Courses(Id) ON DELETE NO ACTION
);

CREATE TABLE Materials (
    Id SERIAL,
    FileFormat INT NOT NULL,
    Data BYTEA NOT NULL,
    SectionId INT NOT NULL,

    CONSTRAINT PK_Items PRIMARY KEY(Id),
    CONSTRAINT FK_Items_Sections FOREIGN KEY(Id) REFERENCES Sections(Id) ON DELETE NO ACTION
);

CREATE TABLE Users(
    Id SERIAL,
    Name VARCHAR(50),
    Email VARCHAR(50),
    Password VARCHAR(255),

    CONSTRAINT PK_Users PRIMARY KEY(Id)
);

CREATE TABLE UserCoursesLists(
    Id SERIAL,
    Name VARCHAR(50) NOT NULL,
    UserId INT NOT NULL,

    CONSTRAINT PK_UserCoursesLists PRIMARY KEY(Id),
    CONSTRAINT FK_UserCoursesLists_Users FOREIGN KEY(Id) REFERENCES Users(Id) ON DELETE NO ACTION
);

CREATE TABLE Courses_UserCoursesLists(
    CourseId INT NOT NULL,
    UserCoursesListId INT NOT NULL,

    CONSTRAINT PK_Courses_UserCoursesLists PRIMARY KEY(CourseId, UserCoursesListId),
    CONSTRAINT FK_Courses FOREIGN KEY(CourseId) REFERENCES Courses(Id) ON DELETE NO ACTION,
    CONSTRAINT FK_UserCoursesLists FOREIGN KEY(UserCoursesListId) REFERENCES UserCoursesLists(Id) ON DELETE NO ACTION
);
