CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO faculty_departments(id, name)
VALUES (1, 'Algebra'),
    (2, 'Probability, Operations Research and Statistics'),
    (3, 'Geometry'),
    (4, 'Differential Equations'),
    (5, 'Computing Systems'),
    (6, 'Information Technologies'),
    (7, 'Complex Analysis and Topology'),
    (8, 'Computer Informatics'),
    (9, 'Mathematical Logic and Applications'),
    (10, 'Mathematical Analysis'),
    (11, 'Mechatronics, Robotics and Mechanics'),
    (12, 'Education in Mathematics and Informatics'),
    (13, 'Software Technologies'),
    (14, 'Numerical Methods and Algorithms');

INSERT INTO user_roles(id, name)
VALUES (1, 'USER'),
    (2, 'ADMIN');

INSERT INTO users(id, name, password_hash, email)
VALUES (1, 'Admin Adminchev', crypt('admin', gen_salt('bf', 10))::VARCHAR(60), 'admin@admin.bg');