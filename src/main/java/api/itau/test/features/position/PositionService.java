package api.itau.test.features.position;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.position.dto.PositionDetailsDto;
import api.itau.test.features.quote.Quote;
import api.itau.test.features.quote.QuoteRepository;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PositionService {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    QuoteRepository quoteRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PositionDetailsDto> getUserPositions(UUID userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        List<Position> positions = positionRepository.findAllByUser_UserId(userId);

        return positions.stream().map(position -> {
            Optional<Quote> latestQuote = quoteRepository.findFirstByAssetOrderByQuoteDateTimeDesc(position.getAsset());

            BigDecimal currentPrice = latestQuote
                    .map(Quote::getQuoteUnityPrice)
                    .orElse(BigDecimal.ZERO);

            BigDecimal profitAndLoss = CalculateProfitAndLoss.calculate(position.getQuantity(), position.getAveragePrice(), currentPrice);
            BigDecimal profitAndLossPercentage = CalculateProfitAndLossPercentage.calculate(position.getAveragePrice(), currentPrice);

            return new PositionDetailsDto(position, currentPrice, profitAndLoss, profitAndLossPercentage);
        }).collect(Collectors.toList());
    }
}
