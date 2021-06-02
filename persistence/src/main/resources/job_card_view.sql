DROP VIEW IF EXISTS job_cards CASCADE;
CREATE VIEW job_cards AS
SELECT job_post.post_id,
       coalesce(avg(review_rate), 0)        AS rating,
       count(distinct contract.contract_id) AS post_contract_count,
       count(DISTINCT review.contract_id)   as reviews,
       package_price          AS min_pack_price,
       min(package_rate_type) AS min_rate_type,
       pi.image_id            AS card_image_id
FROM   job_post
         NATURAL JOIN job_package pack
         LEFT JOIN post_image pi ON job_post.post_id = pi.post_id
         LEFT JOIN contract ON contract.package_id = pack.package_id
         LEFT JOIN review ON review.contract_id = contract.contract_id

WHERE (COALESCE(package_price,0) = (SELECT COALESCE(MIN(package_price),0)
                                    FROM job_package
                                    WHERE post_id = job_post.post_id))
--      para el caso en el que sea un unico paquete con precio a acordar
  AND COALESCE((pi.image_id = (SELECT MIN(image_id)
                               FROM post_image
                               WHERE post_id = job_post.post_id)), TRUE)
--      para el caso en el que no tenga imagenes
GROUP BY job_post.post_id, package_price, pi.image_id;