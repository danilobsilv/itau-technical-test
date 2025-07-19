package api.itau.test.position;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.position.Position;
import api.itau.test.features.position.PositionRepository;
import api.itau.test.features.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PositionFeatureTest {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User testUser1;
    private Asset testAsset1;
    private Asset testAsset2;

    @BeforeEach
    void setup() {
        this.testUser1 = testEntityManager.persistAndFlush(User.builder()
                .userName("Position Test User")
                .userEmail("position.test@example.com")
                .userPercentualCorretagem(new BigDecimal("1.0"))
                .build());

        this.testAsset1 = testEntityManager.persistAndFlush(Asset.builder()
                .assetCode("POSI3")
                .assetName("Position Asset 1")
                .build());

        this.testAsset2 = testEntityManager.persistAndFlush(Asset.builder()
                .assetCode("POSI4")
                .assetName("Position Asset 2")
                .build());
    }

    @Test
    @DisplayName("Must return the positions list of an specific user")
    void findAllByUser_UserId_Success() {
        testEntityManager.persist(createTestPosition(testUser1, testAsset1));
        testEntityManager.persist(createTestPosition(testUser1, testAsset2));

        User userWithNoPositions = testEntityManager.persistAndFlush(User.builder().userName("No Position User").userEmail("nopos@email.com").userPercentualCorretagem(BigDecimal.ONE).build());

        List<Position> foundPositions = positionRepository.findAllByUser_UserId(testUser1.getUserId());

        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions).hasSize(2);
        assertThat(foundPositions.get(0).getUser().getUserId()).isEqualTo(testUser1.getUserId());
    }

    @Test
    @DisplayName("Must return an empty list for an user without positions")
    void findAllByUser_UserId_WhenUserHasNoPositions() {
        List<Position> foundPositions = positionRepository.findAllByUser_UserId(testUser1.getUserId());

        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions).isEmpty();
    }

    @Test
    @DisplayName("Must return an empty list for an user with unexisting ID")
    void findAllByUser_UserId_WhenUserDoesNotExist() {
        UUID nonExistentUserId = UUID.randomUUID();

        List<Position> foundPositions = positionRepository.findAllByUser_UserId(nonExistentUserId);

        assertThat(foundPositions).isNotNull();
        assertThat(foundPositions).isEmpty();
    }

    private Position createTestPosition(User user, Asset asset) {
        return Position.builder()
                .user(user)
                .asset(asset)
                .quantity(new BigDecimal("50"))
                .averagePrice(new BigDecimal("20.00"))
                .build();
    }
}
