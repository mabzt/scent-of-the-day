ALTER TABLE users
    ADD current_temperature DECIMAL;

ALTER TABLE users
    ADD maximum_temperature DECIMAL;

ALTER TABLE users
    ADD minimum_temperature DECIMAL;

-- DROP TABLE jobrunr_backgroundjobservers CASCADE;
--
-- DROP TABLE jobrunr_jobs CASCADE;
--
-- DROP TABLE jobrunr_metadata CASCADE;
--
-- DROP TABLE jobrunr_migrations CASCADE;
--
-- DROP TABLE jobrunr_recurring_jobs CASCADE;