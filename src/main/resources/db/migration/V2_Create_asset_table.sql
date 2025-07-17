CREATE TABLE assets (
    asset_id CHAR(36) PRIMARY KEY,

    asset_code VARCHAR(255) NOT NULL UNIQUE,

    asset_name VARCHAR(255) NOT NULL
);
