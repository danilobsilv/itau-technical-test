package api.itau.test.features.transaction.dto;

import api.itau.test.features.transaction.OperationType;
import api.itau.test.features.transaction.Transaction;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionDetails(
        UUID transactionId,
        String userName,
        String assetCode,
        BigDecimal transactionAmount,
        BigDecimal transactionUnitPrice,
        OperationType transactionOperationType,
        BigDecimal transactionBankBrokerage,
        Instant transactionDateTime
) {
    public TransactionDetails(Transaction transaction){
        this(
            transaction.getTransactionId(),
            transaction.getUser().getUserName(),
            transaction.getAsset().getAssetCode(),
            transaction.getTransactionAmount(),
            transaction.getTransactionUnitPrice(),
            transaction.getTransactionOperationType(),
            transaction.getTransactionBankBrokerage(),
            transaction.getTransactionDateTime()
        );
    }

}
