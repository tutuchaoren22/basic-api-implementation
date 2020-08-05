package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.User;
import com.thoughtworks.rslist.exception.InvalidParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRegisterController {
    public static List<User> userList = new ArrayList<>();

    @PostMapping("/login")
    public ResponseEntity userRegister(@RequestBody @Validated User user, BindingResult bindingResult) throws InvalidParamException {
        if (bindingResult.hasErrors()) {
            throw new InvalidParamException("invalid user");
        }
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
