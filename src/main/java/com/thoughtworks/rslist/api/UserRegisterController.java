package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entities.User;
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

    @PostMapping("/user")
    public void userRegister(@RequestBody @Valid User user) {
        userList.add(user);
    }

    @GetMapping("/user/list")
    public List<User> getUserList() {
        return userList;
    }

}
