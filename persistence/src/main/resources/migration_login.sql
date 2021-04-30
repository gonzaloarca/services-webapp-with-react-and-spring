CREATE TABLE IF NOT EXISTS user_role
(
    user_id INT 	NOT NULL,
    role_id INT     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users,
    PRIMARY KEY (user_id, role_id)
);

DROP VIEW IF EXISTS full_post CASCADE;
CREATE OR REPLACE VIEW full_post AS
SELECT job_post.post_id,
       post_title,
       post_available_hours,
       post_job_type,
       post_is_active,
       user_id,
       user_email,
       user_name,
       user_phone,
       user_is_active,
       user_is_verified,
       user_image,
       users.image_type 					as user_image_type,
       coalesce(avg(review_rate), 0)        AS rating,
       array_agg(DISTINCT zone_id)          as zones,
       count(distinct contract.contract_id) as contracts,
       count(DISTINCT review.contract_id)   as reviews,
       post_creation_date,
       user_creation_date
FROM job_post
         NATURAL JOIN users
         NATURAL JOIN post_zone
         LEFT JOIN job_package ON job_post.post_id = job_package.post_id
         LEFT JOIN contract ON contract.package_id = job_package.package_id
         LEFT JOIN review ON review.contract_id = contract.contract_id
GROUP BY job_post.post_id, post_title, post_available_hours, post_job_type, post_is_active, user_id, user_email,
         user_name, user_phone, user_is_active, user_is_verified, user_image, users.image_type,post_creation_date,
         user_creation_date;

DROP VIEW IF EXISTS full_contract CASCADE;
CREATE OR REPLACE VIEW full_contract AS
SELECT *
FROM contract
         NATURAL JOIN job_package
         NATURAL JOIN (SELECT post_id,
                              post_title,
                              post_job_type,
                              post_available_hours,
                              post_is_active,
                              post_creation_date,
                              array_agg(DISTINCT zone_id) as zones,
                              user_id                     AS professional_id

                       FROM job_post
                                NATURAL JOIN post_zone
                       GROUP BY job_post.post_id, post_title, post_available_hours, post_job_type, post_is_active,
                                user_id) AS posts
         NATURAL JOIN (SELECT user_id        AS client_id,
                              user_email     AS client_email,
                              user_name      AS client_name,
                              user_phone     AS client_phone,
                              user_is_active as client_is_active,
                              user_image	 as client_image,
                              image_type	 as client_image_type,
                              user_creation_date as client_creation_date
                       FROM users) as clients
         NATURAL JOIN (SELECT user_id        AS professional_id,
                              user_email     AS professional_email,
                              user_name      AS professional_name,
                              user_phone     AS professional_phone,
                              user_is_active as professional_is_active,
         					  user_image     as professional_image,
							  image_type	 as professional_image_type,
                              user_creation_date as professional_creation_date

                       FROM users) as professionals;

CREATE TABLE IF NOT EXISTS verification_token
(
	user_id			INT				PRIMARY KEY,
	token			VARCHAR(100)	NOT NULL,
	creation_date	TIMESTAMP		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users
);
