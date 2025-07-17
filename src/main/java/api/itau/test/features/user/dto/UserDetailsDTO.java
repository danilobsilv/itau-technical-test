package api.itau.test.features.user.dto;

import api.itau.test.features.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public record UserDetailsDTO(
        UUID userId,
        String userName,
        String userEmail,
        BigDecimal userPercentualCorretagem

) {
    public UserDetailsDTO(User user){
        this(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserPercentualCorretagem());
    }
}
