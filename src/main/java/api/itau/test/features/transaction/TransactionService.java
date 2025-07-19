package api.itau.test.features.transaction;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.transaction.dto.TransactionDetails;
import api.itau.test.features.transaction.validations.TransactionValidator;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    private List<TransactionValidator> validators;

    @Transactional
    public Transaction saveTransaction(@RequestBody @Valid CreateTransactionDto data){
        validators.forEach(validator -> validator.validate(data));

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + data.userId() + " não encontrado."));
        Asset asset = assetRepository.findById(data.assetId())
                .orElseThrow(() -> new ResourceNotFoundException("Ativo com ID " + data.assetId() + " não encontrado."));

        var transaction = Transaction.builder()
                        .user(user)
                        .asset(asset)
                        .transactionAmount(data.transactionAmount())
                        .transactionUnitPrice(data.transactionUnitPrice())
                        .transactionOperationType(data.operationType())
                        .transactionBankBrokerage(data.transactionBankBrokerage())
                        .transactionDateTime(data.transactionDateTime())
                        .build();

        return transactionRepository.save(transaction);
    }

    @Transactional()
    public Page<TransactionDetails> getAllTransactions(@PageableDefault(size = 10, sort = {"transactionId"}) Pageable pageable){
        return transactionRepository.findAll(pageable).map(TransactionDetails::new);

    }
}
