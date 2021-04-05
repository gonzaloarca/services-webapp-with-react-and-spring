TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(user_id, user_name, user_email, user_phone, user_is_professional, user_is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','11-3456-3232',true,true);
INSERT INTO users(user_id, user_name, user_email, user_phone, user_is_professional, user_is_active) VALUES (DEFAULT,'Manuel Rodriguez','manurodriguez@gmail.com','1109675432',false,true);
COMMIT;