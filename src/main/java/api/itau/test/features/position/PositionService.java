package api.itau.test.features.position;

import api.itau.test.exceptionHandler.InvalidPosition.InvalidPositionException;
import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.position.dto.PositionDetailsDto;
import api.itau.test.features.position.dto.UserPortfolioDto;
import api.itau.test.features.quote.Quote;
import api.itau.test.features.quote.QuoteRepository;
import api.itau.test.features.transaction.Transaction;
import api.itau.test.features.transaction.TransactionRepository;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AssetRepository assetRepository;


    public void updatePositionsForAsset(UUID assetId, BigDecimal newPrice) {
        List<Position> positions = positionRepository.findByAssetId(assetId);

        positions.forEach(position -> {
            position.setCurrentPrice(newPrice);

            BigDecimal pnl = calculateProfitLoss(
                position.getQuantity(),
                position.getAveragePrice(),
                newPrice
            );
            position.setProfitLoss(pnl);

            BigDecimal pnlPercentage = calculateProfitLossPercentage(
                position.getAveragePrice(),
                newPrice
            );
            position.setProfitLossPercentage(pnlPercentage);

            positionRepository.save(position);
        });
    }

    private BigDecimal calculateProfitLoss(BigDecimal quantity, BigDecimal avgPrice, BigDecimal currentPrice) {
        return currentPrice.subtract(avgPrice).multiply(quantity);
    }

    private BigDecimal calculateProfitLossPercentage(BigDecimal avgPrice, BigDecimal currentPrice) {
        if (avgPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return currentPrice.subtract(avgPrice)
               .divide(avgPrice, 4, RoundingMode.HALF_UP)
               .multiply(new BigDecimal("100"));
    }

    public void recalculatePosition(UUID userId, UUID assetId) {
        List<Transaction> transactions = transactionRepository
            .findByUserAndAsset(userId, assetId);

        BigDecimal totalQuantity = calculateTotalQuantity(transactions);
        BigDecimal averagePrice = calculateAveragePrice(transactions);

        Position position = positionRepository.findByUserIdAndAssetId(userId, assetId)
            .orElse(new Position(assetId, userId));

        position.setQuantity(totalQuantity);
        position.setAveragePrice(averagePrice);

        BigDecimal currentPrice = quoteRepository.findLatestByAsset(assetId)
            .orElse(position.getCurrentPrice() != null
                ? position.getCurrentPrice()
                : BigDecimal.ZERO);

        position.setCurrentPrice(currentPrice);
        position.setProfitLoss(calculateProfitLoss(totalQuantity, averagePrice, currentPrice));
        position.setProfitLossPercentage(calculateProfitLossPercentage(averagePrice, currentPrice));

        positionRepository.save(position);
    }

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

    @Cacheable(value = "positions", key = "{#userId, #assetId}")
    public Position getPosition(UUID userId, UUID assetId) {
        return positionRepository.findByUserAndAsset(userId, assetId)
            .orElseThrow(() -> new InvalidPositionException("Invalid position."));
    }

    @Scheduled(fixedRate = 1000)
    public void batchUpdatePositions() {
        List<Asset> activeAssets = assetRepository.findActiveAssets();
        activeAssets.forEach(asset -> {
        BigDecimal latestPrice = quoteRepository.findLatestByAsset(asset.getAssetId())
            .orElse(null);
            if (latestPrice != null) {
                updatePositionsForAsset(asset.getAssetId(), latestPrice);
                }
            }
        );
    }
}
