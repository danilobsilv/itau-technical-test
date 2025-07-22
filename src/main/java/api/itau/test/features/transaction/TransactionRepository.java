package api.itau.test.features.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.user.userId = :userId " +
           "AND t.asset.assetId = :assetId " +
           "AND t.transactionDateTime >= :startDate " +
           "ORDER BY t.transactionDateTime DESC")
    List<Transaction> findByUserAndAssetAndDateAfter(
        @Param("userId") UUID userId,
        @Param("assetId") UUID assetId,
        @Param("startDate") Instant startDate);

    boolean existsByUserUserIdAndAssetAssetId(UUID userId, UUID assetId);
}