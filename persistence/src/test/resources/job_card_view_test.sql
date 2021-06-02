DROP VIEW IF EXISTS job_cards CASCADE;
CREATE VIEW job_cards AS
SELECT full_post.post_id,
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
       user_image_type,
       rating,
       zones,
       contracts AS post_contract_count,
       reviews,
       post_creation_date,
       user_creation_date,
       package_price          AS min_pack_price,
       min(package_rate_type) AS min_rate_type,
       pi.image_id            AS card_image_id
FROM full_post
         NATURAL JOIN job_package pack
         LEFT JOIN post_image pi ON full_post.post_id = pi.post_id
WHERE (COALESCE(package_price,0) = (SELECT COALESCE(MIN(package_price),0)
                                 FROM job_package
                                 WHERE post_id = full_post.post_id))
  AND COALESCE((pi.image_id = (SELECT MIN(image_id)
                               FROM post_image
                               WHERE post_id = full_post.post_id)), TRUE)
GROUP BY full_post.post_id, post_title, post_available_hours, post_job_type, post_is_active,
         user_id, user_email, user_name, user_phone, user_is_active, user_is_verified,
         user_image, user_image_type, rating, zones, contracts, reviews, post_creation_date,
         user_creation_date, package_price, pi.image_id;
