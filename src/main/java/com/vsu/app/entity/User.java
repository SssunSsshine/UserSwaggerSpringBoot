package com.vsu.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String surname;
    private String name;
    private String birthday;
    private String email;
    private String phone;
    private String password;

    public User(String surname, String name, String birthday, String email, String phone, String password) {
        this.surname = surname;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

}
