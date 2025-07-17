package api.itau.test.features.asset.dto;

import jakarta.validation.constraints.NotBlank;

public record InsertAsset(
        @NotBlank
        String assetCode,

        @NotBlank
        String assetName
) {
}
