package com.vsu.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private Long id;
    private Long idUser;
    private String text;
    private String timeCreation;

    public Message(Long idUser, String text) {
        this.idUser = idUser;
        this.text = text;
    }
}
