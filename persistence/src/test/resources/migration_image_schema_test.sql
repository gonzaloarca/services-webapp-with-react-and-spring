CREATE TABLE IF NOT EXISTS post_image
(
    image_id		SERIAL 	PRIMARY KEY,
    post_id			INTEGER NOT NULL,
    image_data		BYTEA 	NOT NULL,
    image_type		VARCHAR(100) NOT NULL default 'unknown',
    FOREIGN KEY (post_id) REFERENCES job_post ON DELETE CASCADE
);
