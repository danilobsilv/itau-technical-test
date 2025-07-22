package api.itau.test.features.transaction.validations;

import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.transaction.dto.RecentTransactionsDto;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ValidateRecentTransactionCustomDate {

    public static Instant getCutoffDate(RecentTransactionsDto data) {
        return data.customDate() != null
            ? data.customDate()
            : Instant.now().minus(30, ChronoUnit.DAYS);
    }

    public  void validate(RecentTransactionsDto data) {
        if (data.customDate() != null && data.customDate().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Custom date cannot be in the future");
        }
    }
}