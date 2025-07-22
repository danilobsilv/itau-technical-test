CREATE INDEX idx_position_user_asset ON positions (user_id, asset_id);
CREATE INDEX idx_quote_asset_date ON quotes (asset_id, quote_datetime DESC);