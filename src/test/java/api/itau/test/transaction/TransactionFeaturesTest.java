package api.itau.test.transaction;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.transaction.OperationType;
import api.itau.test.features.transaction.Transaction;
import api.itau.test.features.transaction.TransactionRepository;
import api.itau.test.features.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TransactionFeaturesTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User testUser;
    private Asset testAsset;

    @BeforeEach
    void setup() {
        this.testUser = testEntityManager.persist(User.builder()
                .userName("Test User")
                .userEmail("transaction.test@example.com")
                .userPercentualCorretagem(new BigDecimal("1.5"))
                .build());

        this.testAsset = testEntityManager.persist(Asset.builder()
                .assetCode("TRN4")
                .assetName("Transaction Test Asset")
                .build());
    }

    @Test
    @DisplayName("Must save a new transaction successfully")
    void saveTransaction_Success() {
        var newTransaction = createTestTransaction();

        Transaction savedTransaction = transactionRepository.save(newTransaction);

        var foundTransaction = testEntityManager.find(Transaction.class, savedTransaction.getTransactionId());

        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction.getTransactionId()).isEqualTo(savedTransaction.getTransactionId());
        assertThat(foundTransaction.getUser().getUserId()).isEqualTo(testUser.getUserId());
        assertThat(foundTransaction.getAsset().getAssetId()).isEqualTo(testAsset.getAssetId());
        assertThat(foundTransaction.getTransactionAmount()).isEqualByComparingTo("100");
    }

    @Test
    @DisplayName("Must find all transactions in a paginated form.")
    void findAllTransactions_Success() {
        testEntityManager.persist(createTestTransaction());
        testEntityManager.persist(createTestTransaction());

        Page<Transaction> transactionPage = transactionRepository.findAll(PageRequest.of(0, 5));

        assertThat(transactionPage).isNotNull();
        assertThat(transactionPage.getTotalElements()).isEqualTo(2);
        assertThat(transactionPage.getContent()).hasSize(2);
        assertThat(transactionPage.getContent().get(0).getUser().getUserId()).isEqualTo(testUser.getUserId());
    }

    /**
     * Helper method to create a test transaction
     */
    private Transaction createTestTransaction() {
        return Transaction.builder()
                .user(testUser)
                .asset(testAsset)
                .transactionAmount(new BigDecimal("100"))
                .transactionUnitPrice(new BigDecimal("25.50"))
                .transactionOperationType(OperationType.BUYING)
                .transactionBankBrokerage(new BigDecimal("4.90"))
                .transactionDateTime(Instant.now())
                .build();
    }
}
