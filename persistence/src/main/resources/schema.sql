CREATE TABLE IF NOT EXISTS users
(
    user_id              SERIAL PRIMARY KEY,
    user_name            VARCHAR(100)        NOT NULL,
    user_email           VARCHAR(100) UNIQUE NOT NULL,
    user_phone           VARCHAR(100)        NOT NULL,
    user_is_professional BOOLEAN             NOT NULL,
    user_is_active       BOOLEAN             NOT NULL
);

CREATE TABLE IF NOT EXISTS job_post
(
    post_id              SERIAL PRIMARY KEY,
    user_id              INTEGER      NOT NULL,
    post_title           VARCHAR(100) NOT NULL,
    post_available_hours VARCHAR(100) NOT NULL,
    post_job_type        INTEGER      NOT NULL,
    post_is_active       BOOLEAN      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_package
(
    package_id          SERIAL PRIMARY KEY,
    post_id             INTEGER          NOT NULL,
    package_title       VARCHAR(100)     NOT NULL,
    package_description TEXT             NOT NULL,
    package_price       DOUBLE PRECISION NOT NULL,
    package_rate_type   INTEGER          NOT NULL,
    package_is_active   BOOLEAN          NOT NULL,
    FOREIGN KEY (post_id) REFERENCES job_post ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post_zone
(
    post_id INTEGER NOT NULL,
    zone_id INTEGER NOT NULL,
    PRIMARY KEY (post_id, zone_id),
    FOREIGN KEY (post_id) REFERENCES job_post ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contract
(
    contract_id          SERIAL PRIMARY KEY,
    client_id            INTEGER,
    package_id           INTEGER,
    creation_date        DATE NOT NULL,
    contract_description TEXT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES users ON DELETE SET NULL,
    FOREIGN KEY (package_id) REFERENCES job_package ON DELETE SET NULL
);