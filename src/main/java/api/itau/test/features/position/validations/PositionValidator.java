package api.itau.test.features.position.validations;

import api.itau.test.features.position.dto.CreatePositionDto;

public interface PositionValidator {
    void validate(CreatePositionDto data);
}
