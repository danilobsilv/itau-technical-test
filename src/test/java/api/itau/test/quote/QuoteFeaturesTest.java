package api.itau.test.quote;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.quote.Quote;
import api.itau.test.features.quote.QuoteRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class QuoteFeaturesTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Asset testAsset;

    @BeforeEach
    void setup() {
        this.testAsset = testEntityManager.persistFlushFind(Asset.builder()
                .assetCode("QTE4")
                .assetName("Quote Test Asset")
                .build());
    }

    @Test
    @DisplayName("Must save a new quote successfully")
    void saveQuote_Success() {
        var newQuote = createTestQuote();

        Quote savedQuote = quoteRepository.save(newQuote);

        var foundQuote = testEntityManager.find(Quote.class, savedQuote.getQuoteId());

        assertThat(foundQuote).isNotNull();
        assertThat(foundQuote.getQuoteId()).isEqualTo(savedQuote.getQuoteId());
        assertThat(foundQuote.getAsset().getAssetId()).isEqualTo(testAsset.getAssetId());
        assertThat(foundQuote.getQuoteUnityPrice()).isEqualByComparingTo("150.75");
    }

    @Test
    @DisplayName("Must return Empty Optional when finding quotes unexisting")
    void findQuoteById_Fail_WhenQuoteDoesNotExist() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<Quote> foundQuote = quoteRepository.findById(nonExistentId);

        assertThat(foundQuote).isEmpty();
    }

    @Test
    @DisplayName("Must find all quotes in a pageable way")
    void findAllQuotes_Success() {
        testEntityManager.persist(createTestQuote());
        testEntityManager.persist(createTestQuote());

        Page<Quote> quotePage = quoteRepository.findAll(PageRequest.of(0, 5));

        assertThat(quotePage).isNotNull();
        assertThat(quotePage.getTotalElements()).isEqualTo(2);
        assertThat(quotePage.getContent()).hasSize(2);
        assertThat(quotePage.getContent().get(0).getAsset().getAssetId()).isEqualTo(testAsset.getAssetId());
    }

    private Quote createTestQuote() {
        return Quote.builder()
                .asset(testAsset)
                .quoteUnityPrice(new BigDecimal("150.75"))
                .quoteDateTime(Instant.now())
                .build();
    }
}
