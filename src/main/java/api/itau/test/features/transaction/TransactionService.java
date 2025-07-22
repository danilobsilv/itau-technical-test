package api.itau.test.features.transaction;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.transaction.dto.RecentTransactionsDto;
import api.itau.test.features.transaction.dto.TransactionDetails;
import api.itau.test.features.transaction.validations.TransactionValidator;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final List<TransactionValidator> validators;

    @Transactional
    public Transaction saveTransaction(CreateTransactionDto data) {
        validators.forEach(validator -> validator.validate(data));

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + data.userId()));

        Asset asset = assetRepository.findById(data.assetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + data.assetId()));

        return transactionRepository.save(
            Transaction.builder()
                .user(user)
                .asset(asset)
                .transactionAmount(data.transactionAmount())
                .transactionUnitPrice(data.transactionUnitPrice())
                .transactionOperationType(data.operationType())
                .transactionBankBrokerage(data.transactionBankBrokerage())
                .transactionDateTime(data.transactionDateTime())
                .build()
        );
    }

    @Transactional(readOnly = true)
    public Page<TransactionDetails> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable)
                .map(TransactionDetails::new);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getRecentTransactions(RecentTransactionsDto data) {
        if (!userRepository.existsById(data.userId())) {
            throw new ResourceNotFoundException("User not found with ID: " + data.userId());
        }
        if (!assetRepository.existsById(data.assetId())) {
            throw new ResourceNotFoundException("Asset not found with ID: " + data.assetId());
        }

        Instant startDate = data.customDate() != null
            ? data.customDate()
            : Instant.now().minus(30, ChronoUnit.DAYS);

        return transactionRepository.findByUserAndAssetAndDateAfter(
            data.userId(),
            data.assetId(),
            startDate
        );
    }
}