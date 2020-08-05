package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRegisterController {
    public static List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public ResponseEntity userRegister(@RequestBody @Valid User user) {
        userList.add(user);
        return ResponseEntity.created(null).build();
    }

}
