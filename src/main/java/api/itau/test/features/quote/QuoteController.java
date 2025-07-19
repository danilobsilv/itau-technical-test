package api.itau.test.features.quote;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.quote.dto.CreateQuoteDto;
import api.itau.test.features.quote.dto.QuoteDetailsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {

    @Autowired
    QuoteService quoteService;

    @PostMapping
    public ResponseEntity<QuoteDetailsDto> createQuote(@RequestBody @Valid CreateQuoteDto data, UriComponentsBuilder uriComponentsBuilder){
        Quote quote = quoteService.createQuote(data);

        var uri = uriComponentsBuilder.path("/quote/{quote_id}").buildAndExpand(quote.getQuoteId()).toUri();

        System.out.println("URI de resposta da cotação: " + uri);
        return ResponseEntity.created(uri).body(new QuoteDetailsDto(quote));
    }

    @GetMapping
    public ResponseEntity<Page<QuoteDetailsDto>> getAllQuotes(@PageableDefault(size = 10, sort = {"quoteId"}) Pageable pageable){
        Page<QuoteDetailsDto> quoteDetailsDtos = quoteService.getAllQuotes(pageable);

        return ResponseEntity.ok(quoteDetailsDtos);
    }
}
