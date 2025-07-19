package api.itau.test.features.user;

import api.itau.test.features.user.dto.InsertUserDTO;
import api.itau.test.features.user.dto.UserDetailsDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDetailsDTO> createUser(@RequestBody @Valid InsertUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.createUser(data);

        var uri = uriComponentsBuilder.path("/user/{user_id}").buildAndExpand(user.getUserId()).toUri();
        System.out.println("URI de resposta do user: " + uri);

        return ResponseEntity.created(uri).body(new UserDetailsDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserDetailsDTO>> getAllUsers(@PageableDefault(size = 10, sort = {"userName"}) Pageable pageable){
        Page<UserDetailsDTO> userDetailsDTOS = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userDetailsDTOS);
    }
}
