DROP VIEW IF EXISTS job_cards CASCADE;
CREATE VIEW job_cards AS
SELECT jp.post_id,
       jp.post_title,
       jp.post_is_active,
       jp.post_job_type,
       jp.post_creation_date,
       jp.user_id,
       COALESCE(AVG(rev.review_rate), 0)    AS rating,
       COUNT(DISTINCT cont.contract_id)     AS post_contract_count,
       COUNT(DISTINCT rev.contract_id)      AS reviews,
       COALESCE(MIN(pack.package_price), 0) AS min_pack_price,
       MIN(pack.package_rate_type)          AS min_rate_type,
       pi.image_id                          AS card_image_id
FROM job_post jp
         NATURAL JOIN job_package pack
         LEFT JOIN (SELECT *
                    FROM contract
                    WHERE contract_state = 6) AS cont ON cont.package_id = pack.package_id
         LEFT JOIN review rev ON rev.contract_id = cont.contract_id
         LEFT JOIN (SELECT MIN(image_id) AS image_id, post_id
                    FROM post_image
                    GROUP BY post_id) AS pi ON pi.post_id = jp.post_id
GROUP BY jp.post_id, pi.image_id;
