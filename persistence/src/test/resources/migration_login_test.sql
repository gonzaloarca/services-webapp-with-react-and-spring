CREATE TABLE IF NOT EXISTS user_role
(
    user_id SERIAL NOT NULL,
    role_id INT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS verification_token
(
    user_id       INT PRIMARY KEY,
    token         VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users
);
