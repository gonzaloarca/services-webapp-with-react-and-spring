-- ELiminamos informacion en caso de que exista
TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

--Usuario con id 1
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Francisco Quesada', 'franquesada@gmail.com', 'password','1147895678', true, true, NOW());

--9 posts activos y 1 inactivo = TOTAL 10
--9 activos y 1 inactivo del usuario 1
--3 de tipo 1, 5 de tipo 2, 2 de tipo 3
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Electricista Matriculado', 'Lun a Viernes 10hs - 14hs', 1, true, NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq,1,'Paseador de perros','Viernes a sabados 09hs - 14hs',3,true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Electricista Matriculado 2', 'Lun a Viernes 10hs - 14hs', 1, true, NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq,1,'Paseador de perros 2','Viernes a sabados 09hs - 14hs',3,true, NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq,1,'Electricista no matriculado 2','Lun a Jueves 13hs - 14hs',1,true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Plomero Matriculado 3', 'Miercoles a Viernes 10hs - 14hs', 2, true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Plomero Matriculado 4  ', 'Miercoles a Viernes 10hs - 14hs', 2, true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Plomero Inactivo', 'Miercoles a Viernes 10hs - 14hs', 2, false, NOW());

--Todos los posts tienen zonas 1 y 2
INSERT INTO post_zone(post_id, zone_id) VALUES (1, 1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1, 2);
INSERT INTO post_zone(post_id, zone_id) VALUES (2, 1);
INSERT INTO post_zone(post_id, zone_id) VALUES (2, 2);
INSERT INTO post_zone(post_id, zone_id) VALUES (3,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (3,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (4,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (4,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (5, 1);
INSERT INTO post_zone(post_id, zone_id) VALUES (5, 2);
INSERT INTO post_zone(post_id, zone_id) VALUES (6, 1);
INSERT INTO post_zone(post_id, zone_id) VALUES (6,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (7,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (7,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (8,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (8,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (9,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (9,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (10,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (10,2);