DO $$
    BEGIN
        BEGIN
            ALTER TABLE users ADD COLUMN user_creation_date TIMESTAMP DEFAULT current_timestamp;
        EXCEPTION
            WHEN duplicate_column THEN
                NULL;
        END;
    END;
$$;
DO $$
    BEGIN
        BEGIN
            ALTER TABLE job_post ADD COLUMN post_creation_date TIMESTAMP DEFAULT current_timestamp;
        EXCEPTION
            WHEN duplicate_column THEN
                NULL;
        END;
    END;
$$;
DO $$
    BEGIN
        BEGIN
            ALTER TABLE contract ADD COLUMN contract_creation_date TIMESTAMP DEFAULT current_timestamp;
        EXCEPTION
            WHEN duplicate_column THEN
                NULL;
        END;
    END;
$$;