package api.itau.test.asset;


import api.itau.test.features.asset.Asset;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.asset.dto.InsertAsset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AssetFeaturesTest {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Must return the Asset Details which are returned after creating an asset")
    void saveAssetSuccess(String assetCode, String assetName){
        var asset = new Asset(new InsertAsset("assetCode1", "assetName1"));
        Asset savedAsset = assetRepository.save(asset);
        var foundAsset = testEntityManager.find(Asset.class, savedAsset.getAssetId());

        assertThat(foundAsset).isNotNull();
        assertThat(foundAsset.getAssetCode()).isEqualTo(assetCode);
        assertThat(foundAsset.getAssetName()).isEqualTo(assetName);
    }

     @Test
    @DisplayName("Deve lançar exceção ao tentar salvar ativo com código duplicado")
    void saveAsset_Fail_WhenCodeIsDuplicated() {
        // ARRANGE: Cria e persiste um primeiro ativo.
        var assetData1 = new InsertAsset("MGLU3", "Magazine Luiza S.A.");
        testEntityManager.persist(new Asset(assetData1));

        // Prepara um segundo ativo com o mesmo código.
        var assetData2 = new InsertAsset("MGLU3", "Nome Diferente Mesmo Código");
        var assetWithDuplicateCode = new Asset(assetData2);

        // ACT & ASSERT: Verifica se a tentativa de salvar o segundo ativo lança a exceção esperada.
        // A restrição UNIQUE na coluna 'asset_code' deve ser acionada.
        assertThrows(DataIntegrityViolationException.class, () -> {
            assetRepository.saveAndFlush(assetWithDuplicateCode);
        });
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar ativo com ID inexistente")
    void findAssetById_Fail_WhenAssetDoesNotExist() {
        // ARRANGE: Gera um ID aleatório que não existe no banco.
        UUID nonExistentId = UUID.randomUUID();

        // ACT: Tenta buscar um ativo com o ID inexistente.
        Optional<Asset> foundAsset = assetRepository.findById(nonExistentId);

        // ASSERT: Verifica se o Optional retornado está vazio.
        assertThat(foundAsset).isEmpty();
    }
}
