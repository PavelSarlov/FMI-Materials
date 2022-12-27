UPDATE users
SET password_hash = crypt('4dm1nch3V', gen_salt('bf', 10))::VARCHAR(60)
WHERE id = 1;

UPDATE users
SET password_hash = crypt('J0hnd03s', gen_salt('bf', 10))::VARCHAR(60)
WHERE id = 2;