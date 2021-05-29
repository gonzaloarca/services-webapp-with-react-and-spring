-- ELiminamos informacion en caso de que exista
TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Francisco Quesada', 'franquesada@gmail.com', 'password','1147895678', true, true, NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (NEXT VALUE FOR job_post_post_id_seq, 1, 'Electricista Matriculado', 'Lun a Viernes 10hs - 14hs', 1, true, NOW());

INSERT INTO post_zone(post_id, zone_id) VALUES (1, 1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1, 2);

INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo no tan simple', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo no tan simple 2', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo Complejo 2', 'Arreglos de canerias', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (NEXT VALUE FOR job_package_package_id_seq, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);