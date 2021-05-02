DROP VIEW IF EXISTS job_cards CASCADE;
CREATE
OR REPLACE VIEW job_cards AS
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
       contracts,
       reviews,
       post_creation_date,
       user_creation_date,
       package_price          AS min_pack_price,
       min(package_rate_type) AS min_rate_type,
       pi.image_id            AS card_image_id,
       pi.image_data          AS card_image_data,
       pi.image_type          AS card_image_type,
       (SELECT COUNT(*)
        FROM full_contract
                 NATURAL JOIN job_package
        WHERE post_id = full_post.post_id)
                              AS post_contract_count,
       (SELECT COUNT(contract_id)
        FROM full_contract
                 NATURAL JOIN review
        WHERE post_id = full_post.post_id)
                              AS post_reviews_size
FROM full_post
         NATURAL JOIN job_package pack
         LEFT JOIN post_image pi ON full_post.post_id = pi.post_id
WHERE COALESCE((package_price = (SELECT MIN(package_price)
                                 FROM job_package
                                 WHERE post_id = full_post.post_id)), TRUE)
--      para el caso en el que sea un unico paquete con precio a acordar
  AND COALESCE((pi.image_id = (SELECT MIN(image_id)
                               FROM post_image
                               WHERE post_id = full_post.post_id)), TRUE)
--      para el caso en el que no tenga imagenes
GROUP BY full_post.post_id, post_title, post_available_hours, post_job_type, post_is_active, user_id,
         user_email, user_name, user_phone, user_is_active, user_is_verified,
         user_image, user_image_type, rating, zones,
         contracts, reviews, post_creation_date, user_creation_date, package_price, pi.image_id, pi.image_data,
         pi.image_type;
