package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.User;
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

    @PostMapping("/login")
    public ResponseEntity userRegister(@RequestBody @Valid User user) {
        userList.add(user);
        return ResponseEntity.created(null)
                .header("index", String.valueOf(userList.indexOf(user)))
                .build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

}
