TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_package RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_image RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','user_password','11-3456-3232',true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT,1,'Electricista de primera mano','Lunes a viernes 10 a 20',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,2);
