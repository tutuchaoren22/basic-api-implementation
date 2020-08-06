package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.User;
import com.thoughtworks.rslist.entities.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRegisterController {
    public static List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;

    public UserRegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    @PostMapping("/login")
//    public ResponseEntity userRegister(@RequestBody @Valid User user, BindingResult bindingResult) throws InvalidParamException {
//        if (bindingResult.hasErrors()) {
//            throw new InvalidParamException("invalid user");
//        }
//        userList.add(user);
//        return ResponseEntity.created(null)
//                .header("index", String.valueOf(userList.indexOf(user)))
//                .build();
//    }
    @PostMapping("/login")
    public ResponseEntity userRegister(@RequestBody @Valid User user) {
        UserEntity userEntity = UserEntity.builder()
                .userName(user.getUserName())
                .age(user.getAge())
                .email(user.getEmail())
                .gender(user.getGender())
                .phone(user.getPhone())
                .votes(user.getVotes())
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

}
