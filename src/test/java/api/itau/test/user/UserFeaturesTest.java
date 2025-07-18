package api.itau.test.user;

import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import api.itau.test.features.user.dto.InsertUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserFeaturesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Must return the User Details which are returned after creating an user")
    void createUserSuccess(String userName, String userEmail, BigDecimal userPercentualCorretagem){
        var user = new User(new InsertUserDTO(userName, userEmail, userPercentualCorretagem));
        var savedUser = testEntityManager.persist(user);

        User foundUser = testEntityManager.find(User.class, savedUser.getUserId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(userName);
        assertThat(foundUser.getUserEmail()).isEqualTo(userEmail);
        assertThat(foundUser.getUserPercentualCorretagem()).isEqualByComparingTo(userPercentualCorretagem);
    }

    @Test
    @DisplayName("Must throw exception when it tries to save an user with duplicated e-mail")
    void createUser_Fail_WhenEmailIsDuplicated() {
        var user1 = new User(new InsertUserDTO("User One", "duplicate.email@example.com", new BigDecimal("10.00")));
        testEntityManager.persist(user1);

        var userData2 = new InsertUserDTO("User Two", "duplicate.email@example.com", new BigDecimal("20.00"));
        var userWithDuplicateEmail = new User(userData2);

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(userWithDuplicateEmail));
    }

    @Test
    @DisplayName("Must return an empty Optional due to searching for an user with unexisting ID")
    void findUserById_Fail_WhenUserDoesNotExist() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<User> foundUser = userRepository.findById(nonExistentId);

        assertThat(foundUser).isEmpty();
    }
}
