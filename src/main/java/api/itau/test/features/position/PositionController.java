package api.itau.test.features.position;

import api.itau.test.features.position.dto.CreatePositionDetailDto;
import api.itau.test.features.position.dto.CreatePositionDto;
import api.itau.test.features.position.dto.PositionDetailsDto;
import api.itau.test.features.position.dto.UserPortfolioDto;
import api.itau.test.features.transaction.dto.TransactionDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserPortfolioDto> getUserPositions(@PathVariable UUID userId){
        return ResponseEntity.ok(positionService.getUserPortfolio(userId));
    }

    @GetMapping("/{userId}/{assetId}")
    public ResponseEntity<PositionDetailsDto> getPosition(
            @PathVariable UUID userId,
            @PathVariable UUID assetId) {

        Position position = positionService.getPosition(userId, assetId);
        return ResponseEntity.ok(new PositionDetailsDto(position));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PositionDetailsDto>> getAllPositions(
            @PathVariable UUID userId) {

        List<Position> positions = positionService.getAllPositions(userId);
        return ResponseEntity.ok(positions.stream()
            .map(PositionDetailsDto::new)
            .toList());
    }

}
