package com.thoughtworks.rslist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class User {

    @NonNull
    @Size(max = 8)
    private String userName;

    @NonNull
    @Min(18)
    @Max(100)
    private Integer age;

    @NonNull
    private String gender;
    private String email;
    private String phone;
    private int votes = 10;

    public User(String userName, Integer age, String gender, String email, String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }
}
