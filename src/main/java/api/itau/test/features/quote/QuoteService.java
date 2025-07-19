package api.itau.test.features.quote;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.quote.dto.CreateQuoteDto;
import api.itau.test.features.quote.dto.QuoteDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class QuoteService {

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    AssetRepository assetRepository;

    @Transactional
    public Quote createQuote(CreateQuoteDto data) {
        Asset asset = assetRepository.findById(data.assetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + data.assetId()));

        var quote = Quote.builder()
                .asset(asset)
                .quoteUnityPrice(data.quoteUnityPrice())
                .quoteDateTime(Instant.now())
                .build();

        return quoteRepository.save(quote);
    }

    @Transactional(readOnly = true)
    public Page<QuoteDetailsDto> getAllQuotes(@PageableDefault(size = 10, sort = {"quoteId"}) Pageable pageable) {
        return quoteRepository.findAll(pageable).map(QuoteDetailsDto::new);
    }
}
