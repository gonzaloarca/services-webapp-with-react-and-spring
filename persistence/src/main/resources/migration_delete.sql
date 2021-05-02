-- DO $$
--     BEGIN
--         BEGIN
            ALTER TABLE users ADD COLUMN IF NOT EXISTS user_creation_date TIMESTAMP DEFAULT current_timestamp;
--         EXCEPTION
--             WHEN duplicate_column THEN
--                 NULL;
--         END;
--     END;
-- $$ LANGUAGE plpgsql;
-- DO $$
--     BEGIN
--         BEGIN
            ALTER TABLE job_post ADD COLUMN IF NOT EXISTS post_creation_date TIMESTAMP DEFAULT current_timestamp;
--         EXCEPTION
--             WHEN duplicate_column THEN
--                 NULL;
--         END;
--     END;
-- $$ LANGUAGE plpgsql;
-- DO $$
--     BEGIN
--         BEGIN
            ALTER TABLE contract ADD COLUMN IF NOT EXISTS contract_creation_date TIMESTAMP DEFAULT current_timestamp;
--         EXCEPTION
--             WHEN duplicate_column THEN
--                 NULL;
--         END;
--     END;
-- $$ LANGUAGE plpgsql;
-- DO $$
--     BEGIN
--         BEGIN
            ALTER TABLE job_post ADD COLUMN IF NOT EXISTS post_is_active BOOLEAN DEFAULT TRUE;
--         EXCEPTION
--             WHEN duplicate_column THEN
--                 NULL;
--         END;
--     END;
-- $$ LANGUAGE plpgsql;
-- DO $$
--     BEGIN
--         BEGIN
            ALTER TABLE review ADD COLUMN IF NOT EXISTS review_creation_date TIMESTAMP DEFAULT current_timestamp;
--         EXCEPTION
--             WHEN duplicate_column THEN
--                 NULL;
--         END;
--     END;
-- $$ LANGUAGE plpgsql;
