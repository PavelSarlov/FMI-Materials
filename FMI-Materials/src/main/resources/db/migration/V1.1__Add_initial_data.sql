CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO faculty_departments(name)
VALUES ('Algebra'),
    ('Probability, Operations Research and Statistics'),
    ('Geometry'),
    ('Differential Equations'),
    ('Computing Systems'),
    ('Information Technologies'),
    ('Complex Analysis and Topology'),
    ('Computer Informatics'),
    ('Mathematical Logic and Applications'),
    ('Mathematical Analysis'),
    ('Mechatronics, Robotics and Mechanics'),
    ('Education in Mathematics and Informatics'),
    ('Software Technologies'),
    ('Numerical Methods and Algorithms');

INSERT INTO user_roles(name)
VALUES ('USER'),
    ('ADMIN');

INSERT INTO users(name, password_hash, email)
VALUES ('Admin Adminchev', crypt('admin', gen_salt('bf', 10))::VARCHAR(60), 'admin@admin.bg');