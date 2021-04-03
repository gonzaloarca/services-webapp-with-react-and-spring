TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(id, username, email, phone, is_professional, is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','11-3456-3232',true,true);
