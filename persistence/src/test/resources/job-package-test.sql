TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_package RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(id, username, email, phone, is_professional, is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','11-3456-3232',true,true);
INSERT INTO job_post(id, user_id, title, available_hours, job_type, is_active) VALUES (DEFAULT,1,'Electricista de primera mano','Lunes a viernes 10 a 20',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,2);
INSERT INTO job_package(id, post_id, title, description, price, rate_type, is_active) VALUES (DEFAULT,1,'Trabajo simple','Arreglo basico de electrodomesticos',200.0,0,true);
