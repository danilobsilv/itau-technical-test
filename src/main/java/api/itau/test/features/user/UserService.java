package api.itau.test.features.user;

import api.itau.test.features.user.dto.InsertUserDTO;
import api.itau.test.features.user.dto.UserDetailsDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User createUser(@RequestBody @Valid InsertUserDTO data) {
        var user = new User(data);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDetailsDTO> getAllUsers(@PageableDefault(size = 10, sort = {"userName"})Pageable pageable){
        return userRepository.findAll(pageable).map(UserDetailsDTO::new);
    }
}
