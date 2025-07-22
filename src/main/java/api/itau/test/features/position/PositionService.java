package api.itau.test.features.position;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.position.dto.CreatePositionDto;
import api.itau.test.features.position.dto.PositionDetailsDto;
import api.itau.test.features.position.dto.UserPortfolioDto;
import api.itau.test.features.position.validations.PositionValidator;
import api.itau.test.features.quote.Quote;
import api.itau.test.features.quote.QuoteRepository;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Service
public class PositionService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuoteRepository quoteRepository;
    @Autowired
    PositionRepository positionRepository;


    @Transactional(readOnly = true)
    public UserPortfolioDto getUserPortfolio(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        List<Position> positions = positionRepository.findAllByUser_UserId(userId);

        List<PositionDetailsDto> positionDetails = positions.stream().map(position -> {
            BigDecimal currentPrice = quoteRepository.findFirstByAssetOrderByQuoteDateTimeDesc(position.getAsset())
                    .map(Quote::getQuoteUnityPrice)
                    .orElse(BigDecimal.ZERO);

            return new PositionDetailsDto(position, currentPrice);
        }).toList();

        BigDecimal totalValue = positionDetails.stream()
                .map(p -> p.quantity().multiply(p.currentPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new UserPortfolioDto(user.getUserId(), user.getUserName(), totalValue, positionDetails);
    }
}
