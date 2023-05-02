package com.vsu.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getSurname(), user.getSurname()) && Objects.equals(getName(), user.getName()) && Objects.equals(getBirthday(), user.getBirthday()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSurname(), getName(), getBirthday(), getEmail(), getPhone(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
