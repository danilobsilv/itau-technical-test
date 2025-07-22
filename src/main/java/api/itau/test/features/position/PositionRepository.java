package api.itau.test.features.position;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

    List<Position> findAllByUser_UserId(UUID userId);

    Optional<Position> findByUserAndAsset(User user, Asset asset);

    Optional<Position> findByUserIdAndAssetId(UUID assetId, UUID userId);

    List<Position> findByAssetId(UUID assetId);
}
