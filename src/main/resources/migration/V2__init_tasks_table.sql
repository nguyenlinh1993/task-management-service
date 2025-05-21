CREATE TABLE tasks
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    task_type          VARCHAR(20),
    status             VARCHAR(20),
    user_id            INT REFERENCES users (id),
    severity           INT,
    steps_to_reproduce TEXT,
    business_value     VARCHAR(100),
    deadline           DATE
);
