ALTER TABLE contract
ADD COLUMN image_data BYTEA;

CREATE TABLE IF NOT EXISTS post_image
(
	image_id		SERIAL 	PRIMARY KEY,
	post_id			INTEGER NOT NULL,
	image_data		BYTEA 	NOT NULL,
	FOREIGN KEY (post_id) REFERENCES job_post ON DELETE CASCADE
);
