package api.itau.test.features.transaction;


import api.itau.test.features.asset.Asset;
import api.itau.test.features.user.User;
import java.time.Instant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "transaction_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    @Column(name = "transaction_unit_price")
    private BigDecimal transactionUnitPrice;

    @Column(name = "transaction_operation_type")
    @Enumerated(EnumType.STRING)
    private OperationType transactionOperationType;

    @Column(name = "transaction_bank_brokerage")
    private BigDecimal transactionBankBrokerage;

    @Column(name = "transaction_datetime")
    private Instant transactionDateTime;

}
