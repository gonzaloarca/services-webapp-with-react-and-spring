ALTER TABLE users ADD COLUMN IF NOT EXISTS user_password VARCHAR(100) NOT NULL DEFAULT md5(random()::text);

CREATE TABLE IF NOT EXISTS user_role(
    user_id SERIAL NOT NULL ,
    role_id INT NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users,
    PRIMARY KEY (user_id,role_id)
);

ALTER TABLE users DROP COLUMN IF EXISTS user_is_professional;

ALTER TABLE IF EXISTS job_package ALTER COLUMN package_price DROP NOT NULL ;