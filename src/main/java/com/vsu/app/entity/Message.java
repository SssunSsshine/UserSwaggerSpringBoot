package com.vsu.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private Long id;
    private Long id_user;
    private String text;
    private String time_creation;

    public Message(Long id_user, String text) {
        this.id_user = id_user;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getId_user(), message.getId_user()) && Objects.equals(getText(), message.getText()) && Objects.equals(getTime_creation(), message.getTime_creation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getId_user(), getText(), getTime_creation());
    }
}
