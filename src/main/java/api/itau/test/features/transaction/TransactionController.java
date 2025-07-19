package api.itau.test.features.transaction;

import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.transaction.dto.TransactionDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDetails> saveTransaction(@RequestBody @Valid CreateTransactionDto data, UriComponentsBuilder uriComponentsBuilder){
        Transaction transaction = transactionService.saveTransaction(data);

        var uri = uriComponentsBuilder.path("/transaction/{transaction_id}").buildAndExpand(transaction.getTransactionId()).toUri();
        System.out.println("URI de resposta da transação: " + uri);

        return ResponseEntity.created(uri).body(new TransactionDetails(transaction));

    }

    @GetMapping
    public ResponseEntity<Page<TransactionDetails>> getAllTransactions(@PageableDefault(size = 10, sort = {"transactionId"}) Pageable pageable){
        Page<TransactionDetails>  transactionDetails = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(transactionDetails);
    }
}
