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
       contracts,
       reviews,
       post_creation_date,
       user_creation_date,
       package_price            as min_pack_price,
       min(package_rate_type)   as min_rate_type,
       pi.image_id              as card_image_id,
       pi.image_data            as card_image_data,
       pi.image_type            as card_image_type,
       (SELECT COUNT(*)
        FROM full_contract
                 NATURAL JOIN job_package
        WHERE post_id = full_post.post_id)
                                as post_contract_count,
       (SELECT COUNT(contract_id)
        FROM full_contract
                 NATURAL JOIN review
        WHERE post_id = full_post.post_id)
                                as post_reviews_size
FROM full_post
         LEFT JOIN job_package pack on pack.post_id = full_post.post_id
         LEFT JOIN post_image pi on full_post.post_id = pi.post_id
WHERE package_price = (SELECT min(package_price) FROM job_package
                       WHERE post_id = full_post.post_id)
  AND pi.image_id = (SELECT min(image_id) FROM post_image
                     WHERE post_id = full_post.post_id)
GROUP BY full_post.post_id, post_title, post_available_hours, post_job_type, post_is_active, user_id,
         user_email, user_name, user_phone, user_is_active, user_is_verified, user_image, user_image_type, rating, zones,
         contracts, reviews, post_creation_date, user_creation_date, package_price, pi.image_id, pi.image_data, pi.image_type
;
