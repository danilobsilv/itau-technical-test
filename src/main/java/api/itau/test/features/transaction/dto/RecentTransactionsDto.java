package api.itau.test.features.transaction.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;
import java.util.UUID;

public record RecentTransactionsDto(
        @NotNull
        UUID userId,

        @NotNull
        UUID assetId,

        @PastOrPresent(message = "There cannot be transactions on the future.")
        Instant customDate
) {
}
