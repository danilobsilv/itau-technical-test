package api.itau.test.features.quote;

import api.itau.test.features.asset.Asset;
import api.itau.test.features.quote.dto.CreateQuoteDto;
import jakarta.persistence.*;
// ADICIONE ESTES IMPORTS DO LOMBOK
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "quote_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID quoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "quote_unity_price", nullable = false)
    private BigDecimal quoteUnityPrice;

    @Column(name = "quote_date_time", nullable = false)
    private Instant quoteDateTime;

}
