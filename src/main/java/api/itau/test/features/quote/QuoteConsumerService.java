package api.itau.test.features.quote;

import api.itau.test.features.position.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteConsumerService {

    @Autowired
    PositionService positionService;

    @KafkaListener(topics = "quotes-updates")
    public void consumeQuoteUpdate(QuoteUpdate quoteUpdate) {
        positionService.updatePositionsForAsset(quoteUpdate.getAssetId(), quoteUpdate.getNewPrice());
    }
}
