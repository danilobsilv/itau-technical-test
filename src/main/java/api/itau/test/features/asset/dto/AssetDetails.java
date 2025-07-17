package api.itau.test.features.asset.dto;

import api.itau.test.features.asset.Asset;

import java.util.UUID;

public record AssetDetails(
        UUID assetId,
        String assetCode,
        String assetName
) {

    public AssetDetails(Asset asset){
        this(asset.getAssetId(), asset.getAssetCode(), asset.getAssetName());
    }
}
