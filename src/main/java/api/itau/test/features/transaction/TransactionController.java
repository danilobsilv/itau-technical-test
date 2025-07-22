package api.itau.test.features.transaction;

import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.transaction.dto.RecentTransactionsDto;
import api.itau.test.features.transaction.dto.TransactionDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDetails> createTransaction(@RequestBody @Valid CreateTransactionDto data, UriComponentsBuilder uriBuilder) {

        Transaction transaction = transactionService.saveTransaction(data);

        URI location = uriBuilder.path("/transaction/{id}")
                .buildAndExpand(transaction.getTransactionId())
                .toUri();

        return ResponseEntity.created(location)
                .body(new TransactionDetails(transaction));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDetails>> getAllTransactions(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    @GetMapping("/recent/userId/{userId}/assetId/{assetId}")
    public ResponseEntity<Map<String, Object>> getRecentTransactions(
        @PathVariable UUID userId,
        @PathVariable UUID assetId,
        @RequestParam(required = false) Instant customDate) {

        RecentTransactionsDto data = new RecentTransactionsDto(userId, assetId, customDate);

        List<Transaction> transactions = transactionService.getRecentTransactions(data);

        Instant endDate = Instant.now();
        Instant startDate = data.customDate() != null
            ? data.customDate()
            : endDate.minus(30, ChronoUnit.DAYS);

        List<TransactionDetails> transactionDetails = transactions.stream()
                .map(TransactionDetails::new)
                .toList();

        return ResponseEntity.ok(Map.of(
            "status", "success",
            "data", transactionDetails,
            "meta", Map.of(
                "userId", data.userId(),
                "assetId", data.assetId(),
                "count", transactionDetails.size(),
                "timeRange", Map.of(
                    "from", startDate.toString(),
                    "to", endDate.toString()
                )
            )
        ));
    }
}