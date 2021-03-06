package com.thoughtworks.rslist.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    @NotNull
    @Size(max = 8)
    private String userName;

    @NotNull
    @Min(18)
    @Max(100)
    private Integer age;

    @NotNull
    private String gender;

    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "1\\d{10}")
    private String phone;

    private int votes = 10;

    public User() {

    }

    public User(String userName, Integer age, String gender, String email, String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("user_age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("user_gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("user_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("user_phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
