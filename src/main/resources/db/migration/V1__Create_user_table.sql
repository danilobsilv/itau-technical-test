CREATE TABLE users (
    user_id CHAR(36) PRIMARY KEY,

    user_name VARCHAR(255) NOT NULL,

    user_email VARCHAR(255) NOT NULL UNIQUE,

    user_percentual_corretagem DECIMAL(5, 2) NOT NULL
);