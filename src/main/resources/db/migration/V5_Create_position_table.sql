CREATE TABLE positions (
    position_id CHAR(36) PRIMARY KEY,

    user_id CHAR(36) NOT NULL,

    asset_id CHAR(36) NOT NULL,

    quantity DECIMAL(19, 8) NOT NULL,

    average_price DECIMAL(19, 4) NOT NULL,

    CONSTRAINT uk_user_asset UNIQUE (user_id, asset_id),

    CONSTRAINT fk_positions_on_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_positions_on_asset FOREIGN KEY (asset_id) REFERENCES assets (asset_id)
);
