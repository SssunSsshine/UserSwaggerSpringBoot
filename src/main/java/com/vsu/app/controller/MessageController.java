package com.vsu.app.controller;

import com.vsu.app.entity.Message;
import com.vsu.app.request.CreateMessageRequest;
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
        MessageDto message = messageService.insertMessage(new Message(messageRequest.getId_user(),
                messageRequest.getText()));

        if (message != null)
            return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>("Message is not inserted", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/messages/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        Long idL = Long.parseLong(id);
        if (messageService.deleteMessage(idL))
            return new ResponseEntity<>("Message is deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Message is not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/messages/update/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid CreateMessageRequest messageRequest) {
        Long idL = Long.parseLong(id);
        MessageDto message = messageService.getById(idL);

        if (message == null) {
            return new ResponseEntity<>("Message is not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Message updatedMessage = new Message(message.getId(), messageRequest.getId_user(),
                messageRequest.getText(), message.getTime_creation());

        MessageDto updMessage = messageService.updateMessage(updatedMessage);
        if (updMessage != null)
            return new ResponseEntity<>(updMessage, HttpStatus.OK);
        return new ResponseEntity<>("Message is not updated", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
