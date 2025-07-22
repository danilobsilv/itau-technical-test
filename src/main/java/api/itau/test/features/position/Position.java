package api.itau.test.features.position;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "positions")
public class Position {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "position_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "quantity", precision = 19, scale = 8, nullable = false)
    private BigDecimal quantity;

    @Column(name = "average_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal averagePrice;

    @Column(name = "current_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal currentPrice;

    @Column(name = "profit_loss", precision = 19, scale = 4, nullable = false)
    private BigDecimal profitLoss;

    @Column(name = "profit_loss_percentage", precision = 19, scale = 4, nullable = false)
    private BigDecimal profitLossPercentage;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

}