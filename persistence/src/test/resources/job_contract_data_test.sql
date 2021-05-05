TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_package RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE contract RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE review RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_image RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified) VALUES (DEFAULT, 'Francisco Quesada', 'franquesada@gmail.com', 'password','1147895678', true, true);
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified) VALUES (DEFAULT, 'Manuel Rodriguez', 'manurodriguez@gmail.com','password', '1109675432', true, true);
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified) VALUES (DEFAULT, 'Gonzalo Arca', 'gonzaarca@gmail.com','password', '0549940406521', true, true);
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified) VALUES (DEFAULT, 'Manuel Parma', 'manuparma@gmail.com','password', '1158586363', true, true);
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active, user_is_verified) VALUES (DEFAULT, 'Julian Sicardi', 'juliansicardi@gmail.com','password', '123123123', true, true);

INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Electricista Matriculado', 'Lun a Viernes 10hs - 14hs', 1, true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Paseador de perros','Viernes a sabados 09hs - 14hs',3,true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Electricista Matriculado 2', 'Lun a Viernes 10hs - 14hs', 1, true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Paseador de perros 2','Viernes a sabados 09hs - 14hs',3,true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Electricista no matriculado 2','Lun a Jueves 13hs - 14hs',1,true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Plomero Matriculado 2', 'Miercoles a Viernes 10hs - 14hs', 2, true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Plomero Matriculado 3', 'Miercoles a Viernes 10hs - 14hs', 2, true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Plomero Matriculado 4  ', 'Miercoles a Viernes 10hs - 14hs', 2, true);
INSERT INTO job_post(post_id, user_id, post_title, post_available_hours, post_job_type, post_is_active) VALUES (DEFAULT, 1, 'Plomero Inactivo', 'Miercoles a Viernes 10hs - 14hs', 2, false);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,2,'Electricista no matriculado','Lun a Jueves 13hs - 14hs',1,true);

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

INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo no tan simple', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo no tan simple 2', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Complejo 2', 'Arreglos de canerias', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo barato 2', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Experto 2', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 2, 'Trabajo Complejo', 'Arreglos de canerias', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 3, 'Trabajo barato', 'Arreglos varios', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 4, 'Trabajo Experto', 'Presupuesto y desarrollo de proyectos', 500.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 5, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 6, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 7, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 8, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 9, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 10, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 11, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);

INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una zapatilla');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles facil');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una zapatilla');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles facil');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Arreglo de fusibles');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Instalacion de tomacorrientes');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio una tuberia en la cocina');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompieron las tuberias del baño');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 3, 10, '2021-05-04 01:09:46.0', 'Instalacion de tomacorrientes');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 4, 10, '2021-05-04 01:09:46.0', 'Se me rompio una tuberia en la cocina');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 3, 10, '2021-05-04 01:09:46.0', 'Se me rompieron las tuberias del banio');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 4, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 4, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 5, 10, '2021-05-04 01:09:46.0', 'Se me rompio la caldera denuevo');
COMMIT;