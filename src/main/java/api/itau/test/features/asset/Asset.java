package api.itau.test.features.asset;

import api.itau.test.features.asset.dto.InsertAsset;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "asset_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID assetId;


    @Column(name = "asset_code", nullable = false)
    private String assetCode;

    @Column(name = "asset_name", nullable = false)
    private String assetName;

    public Asset(InsertAsset data){
        this.assetCode = data.assetCode();
        this.assetName = data.assetName();
    }
}
