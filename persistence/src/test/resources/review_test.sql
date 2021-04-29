TRUNCATE TABLE job_post RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE job_package RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE post_zone RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE contract RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE review RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active) VALUES (DEFAULT,'Francisco Quesada','franquesada@mail.com','user_password','11-3456-3232',true);
INSERT INTO users(user_id, user_name, user_email,user_password, user_phone, user_is_active) VALUES (DEFAULT,'Manuel Rodriguez','manurodriguez@gmail.com','user_password','1109675432',true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Electricista Matriculado','Lun a Viernes 10hs - 14hs',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (1,2);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,2,'Electricista no matriculado','Lun a Jueves 13hs - 14hs',1,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (2,1);
INSERT INTO post_zone(post_id, zone_id) VALUES (2,3);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo Simple', 'Arreglos de tomacorrientes', 200.00, 0, true);
INSERT INTO job_package(package_id, post_id, package_title, package_description, package_price, package_rate_type, package_is_active) VALUES (DEFAULT, 1, 'Trabajo no tan simple', 'Instalacion de cableado electrico', 850.00, 0, true);
INSERT INTO job_post(post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active) VALUES (DEFAULT,1,'Paseador de perros','Viernes a sabados 09hs - 14hs',3,true);
INSERT INTO post_zone(post_id, zone_id) VALUES (3,4);
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, NOW(), 'Se me rompio una zapatilla');
INSERT INTO contract(contract_id, client_id, package_id, contract_creation_date, contract_description) VALUES (DEFAULT, 2, 1, NOW(), 'Arreglo de fusibles');
INSERT INTO review(contract_id, review_rate, review_title, review_description) VALUES (1, 4, 'Muy bueno', 'Resolvio todo en cuestion de minutos');
INSERT INTO review(contract_id, review_rate, review_title, review_description) VALUES (2, 2, 'Medio pelo', 'Resolvio todo de forma ideal');
COMMIT;