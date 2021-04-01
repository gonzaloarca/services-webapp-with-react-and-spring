CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR (100) NOT NULL,
    is_professional BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS job_post (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title VARCHAR(100) NOT NULL,
    available_hours VARCHAR (100) NOT NULL,
    job_type INTEGER NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_package (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    rate_type INTEGER NOT NULL,
    FOREIGN KEY(post_id) REFERENCES job_post ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post_zone (
    post_id INTEGER NOT NULL,
    zone_id INTEGER NOT NULL,
    PRIMARY KEY (post_id, zone_id),
    FOREIGN KEY(post_id) REFERENCES job_post ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contract (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    client_id INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY(post_id) REFERENCES job_post ON DELETE SET NULL,
    FOREIGN KEY(client_id) REFERENCES users ON DELETE SET NULL
)
