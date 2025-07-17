package api.itau.test.features.asset;

import api.itau.test.features.asset.dto.AssetDetails;
import api.itau.test.features.asset.dto.InsertAsset;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {

    @Autowired
    AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetDetails> saveAsset(@RequestBody @Valid InsertAsset data, UriComponentsBuilder uriComponentsBuilder){
        return assetService.saveAsset(data, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<AssetDetails>> getAllAssets(@PageableDefault(size = 10, sort = {"assetName"}) Pageable pageable){
        return assetService.getAllAssets(pageable);
    }
}
