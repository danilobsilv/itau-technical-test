CREATE TABLE transactions (
    transaction_id CHAR(36) PRIMARY KEY,

    user_id CHAR(36) NOT NULL,

    asset_id CHAR(36) NOT NULL,

    transaction_amount DECIMAL(19, 8) NOT NULL,

    transaction_unit_price DECIMAL(19, 4) NOT NULL,

    transaction_operation_type ENUM('BUY', 'SELL') NOT NULL,

    transaction_bank_brokerage DECIMAL(19, 4) NOT NULL,

    transaction_datetime DATETIME(6) NOT NULL,

    CONSTRAINT fk_transactions_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_transactions_asset FOREIGN KEY (asset_id) REFERENCES assets (asset_id)
);

CREATE INDEX idx_transaction_user_asset_date ON transactions (user_id, asset_id, transaction_datetime DESC);