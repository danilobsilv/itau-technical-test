package api.itau.test.features.asset;

import api.itau.test.features.asset.dto.AssetDetails;
import api.itau.test.features.asset.dto.InsertAsset;
import api.itau.test.features.user.dto.UserDetailsDTO;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;

    public ResponseEntity<AssetDetails> saveAsset(@RequestBody @Valid InsertAsset data, UriComponentsBuilder uriComponentsBuilder){
        var asset = new Asset(data);
        assetRepository.save(asset);

        var uri = uriComponentsBuilder.path("/asset/{asset_id}").buildAndExpand(asset.getAssetId()).toUri();
        System.out.println("URI de resposta do asset: " + uri);

        return ResponseEntity.created(uri).body(new AssetDetails(asset));
    }

    public ResponseEntity<Page<AssetDetails>> getAllAssets(@PageableDefault(size = 10, sort = {"assetName"}) Pageable pageable){
        var page = assetRepository.findAll(pageable).map(AssetDetails::new);

        return ResponseEntity.ok(page);
    }
}
