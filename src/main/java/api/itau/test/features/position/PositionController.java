package api.itau.test.features.position;

import api.itau.test.features.position.dto.PositionDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PositionDetailsDto>> getUserPositions(UUID userId){
        return ResponseEntity.ok(positionService.getUserPositions(userId));
    }
}
