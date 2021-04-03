TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(id,username,email,phone,is_professional,is_active) VALUES (DEFAULT,'Manuel Rodriguez','manurodriguez@gmail.com','1109675432',false,true);
INSERT INTO job_post(id,user_id,title,available_hours,job_type,is_active) VALUES (DEFAULT,1,'Electricista Matriculado','Lun a Viernes 10hs - 14hs',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,2);
COMMIT;