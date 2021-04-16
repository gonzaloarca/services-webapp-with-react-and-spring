TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(user_id,user_name,user_email,user_phone,user_is_active) VALUES (DEFAULT,'Manuel Rodriguez','manurodriguez@gmail.com','1109675432',false,true);
INSERT INTO users(user_id,user_name,user_email,user_phone,user_is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','11-3456-3232',true,true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Electricista Matriculado','Lun a Viernes 10hs - 14hs',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,2);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,2,'Electricista no matriculado','Lun a Jueves 13hs - 14hs',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (2,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (2,3);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Paseador de perros','Viernes a sabados 09hs - 14hs',3,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (3,4);
COMMIT;