package api.itau.test.features.quote;

import api.itau.test.features.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID> {
    Optional<Quote> findFirstByAssetOrderByQuoteDateTimeDesc(Asset asset);
}
