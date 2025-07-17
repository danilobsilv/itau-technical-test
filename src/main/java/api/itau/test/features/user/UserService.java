package api.itau.test.features.user;

import api.itau.test.features.user.dto.InsertUserDTO;
import api.itau.test.features.user.dto.UserDetailsDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public ResponseEntity<UserDetailsDTO> createUser(@RequestBody @Valid InsertUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        var user = new User(data);
        userRepository.save(user);

        var uri = uriComponentsBuilder.path("/user/{user_id}").buildAndExpand(user.getUserId()).toUri();
        System.out.println("URI de resposta do user: " + uri);

        return ResponseEntity.created(uri).body(new UserDetailsDTO(user));

    }

    public ResponseEntity<Page<UserDetailsDTO>> getAllUsers(@PageableDefault(size = 10, sort = {"userName"})Pageable pageable){
        var page = userRepository.findAll(pageable).map(UserDetailsDTO::new);

        return ResponseEntity.ok(page);
    }


}
