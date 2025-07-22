ALTER TABLE positions
ADD COLUMN current_price DECIMAL(19, 4) NOT NULL DEFAULT 0.0000,
ADD COLUMN profit_loss_percentage DECIMAL(19, 4) NOT NULL DEFAULT 0.0000,
ADD COLUMN last_updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
ADD COLUMN version BIGINT NOT NULL DEFAULT 0,
MODIFY COLUMN profit_loss DECIMAL(19, 4) NOT NULL DEFAULT 0.0000;

CREATE INDEX idx_position_user_asset ON positions (user_id, asset_id);
CREATE INDEX idx_position_last_updated ON positions (last_updated);

ALTER TABLE positions
ALTER COLUMN current_price DROP DEFAULT,
ALTER COLUMN profit_loss_percentage DROP DEFAULT,
ALTER COLUMN profit_loss DROP DEFAULT;