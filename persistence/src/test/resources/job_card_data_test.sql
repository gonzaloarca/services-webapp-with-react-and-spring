-- ELiminamos informacion en caso de que exista
TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

--11 usuarios
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Francisco Quesada', 'franquesada@gmail.com', 'password','1147895678', true, false, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Manuel Rodriguez', 'manurodriguez@gmail.com','password', '1109675432', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Gonzalo Arca', 'gonzaarca@gmail.com','password', '0549940406521', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Manuel Parma', 'manuparma@gmail.com','password', '1158586363', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Julian Sicardi', 'juliansicardi@gmail.com','password', '123123123', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Agustin Jerusalinsky', 'pelusa@gmail.com', 'password','12345678', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Salustiano Zavalia', 'zalus@gmail.com','password', '78675645', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Nicolas Papa', 'npapa@gmail.com','password', '09876654321354', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Panuel Marma', 'pmarma@gmail.com','password', '8798767876', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Pedrito paco', 'paco@gmail.com','password', '555555555', true, true, NOW());
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified, user_creation_date) VALUES (NEXT VALUE FOR users_user_id_seq, 'Soledad del Cielo', 'tren@gmail.com','password', '87876767', true, true, NOW());

--12 posts activos y 1 inactivo = TOTAL 13
--9 activos y 1 inactivo del usuario 1, 1 activo del usuario 3, 1 activo del usuario 8, 1 activo del 11
--4 de tipo 1, 5 de tipo 2, 4 de tipo 3
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active,post_creation_date) VALUES (1, 1, 'Electricista Matriculado', 'Lun a Viernes 10hs - 14hs', 1, true,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (2,1,'Paseador de perros','Viernes a sabados 09hs - 14hs',3,true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (3, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (4, 1, 'Electricista Matriculado 2', 'Lun a Viernes 10hs - 14hs', 1, true,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (5,1,'Paseador de perros 2','Viernes a sabados 09hs - 14hs',3,true,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (6,1,'Electricista no matriculado 2','Lun a Jueves 13hs - 14hs',1,true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (7, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (8, 1, 'Plomero Matriculado 3', 'Miercoles a Viernes 10hs - 14hs', 2, true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (9, 1, 'Plomero Matriculado 4  ', 'Miercoles a Viernes 10hs - 14hs', 2, true,NOW());
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active, post_creation_date) VALUES (10, 1, 'Plomero Inactivo', 'Miercoles a Viernes 10hs - 14hs', 2, false,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (11,3,'Electricista no matriculado','Lun a Jueves 13hs - 14hs',1,true,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (12,8,'Paseador de gatos','Sabados de 8hs - 14hs',3,true,NOW());
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active, post_creation_date) VALUES (13,11,'Paseador de urones','Domingos de 8hs - 14hs',3,true,NOW());

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
INSERT INTO post_zone(post_id, zone_id) VALUES (11,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (11,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (12,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (12,2);
INSERT INTO post_zone(post_id, zone_id) VALUES (13,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (13,2);

--21 paquetes
--9 paquetes del post 1 y luego 1 paquete para cada del resto
--Todos de rate type 0
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (1, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (2, 1, 'Trabajo no tan simple', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (3, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (4, 1, 'Trabajo no tan simple 2', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (5, 1, 'Trabajo Complejo 2', 'Arreglos de canerias', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (6, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (7, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (8, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (9, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (10, 2, 'Paseo rapido', '5 cuadras', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (11, 3, 'Trabajo barato', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (12, 4, 'Trabajo Experto', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (13, 5, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (14, 6, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (15, 7, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (16, 8, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (17, 9, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (18, 10, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (19, 11, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (20, 12, 'Trabajo Simple', 'Paseo tardio', 100.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (21, 13, 'Trabajo Simple', 'Paseo recreativo', 100.00, 0, true);

--23 contratos
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (1, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una zapatilla');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (2, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles facil');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (3, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (4, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una zapatilla');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (5, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles facil');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (6, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (7, 2, 1, '2021-05-04 01:09:46.0', 'Instalacion de tomacorrientes');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (8, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una tuberia en la cocina');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (9, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (10, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompieron las tuberias del baño');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (11, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (12, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (13, 3, 10, '2021-05-04 01:09:46.0', 'Instalacion de tomacorrientes');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (14, 4, 10, '2021-05-04 01:09:46.0', 'Se me rompio una tuberia en la cocina');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (15, 3, 10, '2021-05-04 01:09:46.0', 'Se me rompieron las tuberias del banio');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (16, 2, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (17, 4, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (18, 5, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (19, 2, 19, NOW(), 'Arreglo electrico');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (20, 8, 19, NOW(), 'Arreglo electrico');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (21, 2, 20, NOW(), 'Paseo 1');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (22, 4, 20, NOW(), 'Paseo 2');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (23, 2, 21, NOW(), 'Paseo viernes');

COMMIT;