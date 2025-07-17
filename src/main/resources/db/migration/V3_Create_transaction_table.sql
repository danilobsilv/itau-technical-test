CREATE TABLE transactions (
    transaction_id CHAR(36) PRIMARY KEY,

    user_id CHAR(36) NOT NULL,

    asset_id CHAR(36) NOT NULL,

    transaction_amount INT NOT NULL,

    transaction_unit_price DECIMAL(19, 4) NOT NULL,

    transaction_operation_type VARCHAR(255) NOT NULL,

    transaction_bank_brokerage DECIMAL(19, 4) NOT NULL,

    transaction_datetime TIMESTAMP NOT NULL,

    CONSTRAINT fk_transactions_on_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_transactions_on_asset FOREIGN KEY (asset_id) REFERENCES assets (asset_id)
);
