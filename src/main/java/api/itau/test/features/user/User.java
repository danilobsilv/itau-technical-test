package api.itau.test.features.user;

import api.itau.test.features.user.dto.InsertUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR) // *need that to save the uuid as a string*
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "char(36)")
    private UUID userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_percentual_corretagem", nullable = false)
    private BigDecimal userPercentualCorretagem;

    public User(InsertUserDTO data){
        this.userName = data.userName();
        this.userEmail = data.userEmail();
        this.userPercentualCorretagem = data.userPercentualCorretagem();
    }
}
