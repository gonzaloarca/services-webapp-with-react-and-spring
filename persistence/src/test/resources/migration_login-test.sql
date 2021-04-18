ALTER TABLE users ADD COLUMN user_password VARCHAR(100) DEFAULT 'test' NOT NULL ;

CREATE TABLE IF NOT EXISTS user_role(
                                        user_id SERIAL NOT NULL ,
                                        role_id INT NOT NULL ,
                                        FOREIGN KEY (user_id) REFERENCES users,
                                        PRIMARY KEY (user_id,role_id)
);

ALTER TABLE users DROP COLUMN user_is_professional;

-- ALTER TABLE job_package ALTER COLUMN package_price DROP NOT NULL ;