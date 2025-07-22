package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.asset.AssetRepository;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetExists implements TransactionValidator{

    @Autowired
    AssetRepository assetRepository;

    @Override
    public void validate(CreateTransactionDto data) {
        boolean assetExists = assetRepository.existsById(data.assetId());

        if (!assetExists){
            throw new ResourceNotFoundException("Asset not found: " + data.assetId());
        }
    }
}
