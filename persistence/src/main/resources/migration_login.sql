ALTER TABLE users ADD COLUMN IF NOT EXISTS user_password VARCHAR(100) NOT NULL DEFAULT md5(random()::text);

CREATE TABLE IF NOT EXISTS user_role(
    user_id SERIAL NOT NULL ,
    role_id INT NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users,
    PRIMARY KEY (user_id,role_id)
);

ALTER TABLE users DROP COLUMN IF EXISTS user_is_professional;

ALTER TABLE IF EXISTS job_package ALTER COLUMN package_price DROP NOT NULL ;


CREATE OR REPLACE VIEW full_posts AS
SELECT post_id,post_title,post_available_hours,post_job_type,post_is_active,user_id,user_email,user_name,user_phone,user_is_active,avg(rate) as rating,array_agg(zone_id) as zones
FROM job_post
         NATURAL JOIN users
         NATURAL JOIN job_package
         NATURAL JOIN contract
         NATURAL JOIN review
         NATURAL JOIN post_zone
GROUP BY post_id, post_title, post_available_hours, post_job_type, post_is_active, user_id, user_email, user_name, user_phone, user_is_active