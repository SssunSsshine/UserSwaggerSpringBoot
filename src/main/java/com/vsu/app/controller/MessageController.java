package com.vsu.app.controller;

import com.vsu.app.entity.Message;
import com.vsu.app.request.CreateMessageRequest;
import com.vsu.app.request.CreateMessageRequestForUpdate;
import com.vsu.app.response.MessageDto;
import com.vsu.app.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/{id}")
    public MessageDto getById(@PathVariable Long id) {
        try {
            return messageService.getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/messages/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid CreateMessageRequest messageRequest) {
        MessageDto message = messageService.insertMessage(new Message(messageRequest.getIdUser(),
                messageRequest.getText()));

        if (message != null)
            return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>("Message is not inserted", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("users/{idUser}/messages/delete/{idMessage}")
    public String delete(@PathVariable Long idUser, @PathVariable Long idMessage) {
        messageService.deleteMessage(idMessage, idUser);
        return "Message is deleted successfully";
    }

    @PostMapping("users/{idUser}/messages/update/{idMessage}")
    public MessageDto update(@PathVariable Long idUser, @PathVariable Long idMessage, @RequestBody @Valid CreateMessageRequestForUpdate messageRequest) {
        return messageService.updateMessage(idMessage, idUser);
    }
}
