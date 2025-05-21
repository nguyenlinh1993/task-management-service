CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(100),
    full_name   VARCHAR(255),
    delete_flag BOOLEAN DEFAULT FALSE
);
