CREATE TABLE quotes (
    quote_id CHAR(36) PRIMARY KEY,

    asset_id CHAR(36) NOT NULL,

    quote_unity_price DECIMAL(19, 4) NOT NULL,

    quote_date_time TIMESTAMP NOT NULL,

    CONSTRAINT fk_quotes_on_asset FOREIGN KEY (asset_id) REFERENCES assets (asset_id)
);
