package com.thoughtworks.rslist.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String userName;

    private int age;

    private String gender;

    private String email;

    private String phone;

    private int votes;
}
