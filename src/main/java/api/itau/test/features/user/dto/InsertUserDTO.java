package api.itau.test.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InsertUserDTO(
        @NotBlank
        String userName,

        @NotBlank
        @Email
        String userEmail,

        @NotNull
        BigDecimal userPercentualCorretagem
) {
}
