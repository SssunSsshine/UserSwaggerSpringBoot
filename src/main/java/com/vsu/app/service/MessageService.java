package com.vsu.app.service;

import com.vsu.app.entity.Message;
import com.vsu.app.exception.DBException;
import com.vsu.app.repository.MessageRepository;
import com.vsu.app.response.MessageDto;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MessageService {

    private static final Logger LOGGER = Logger.getLogger(MessageService.class.getName());
    public static final int COUNT_UPDATED_ROWS = 1;
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageDto getById(Long id) {
        return fromMessageToMessageDto(messageRepository.getById(id));
    }

    public MessageDto insertMessage(Message message) {
        return fromMessageToMessageDto(messageRepository.insert(message));
    }

    public boolean deleteMessage(Long id) {
        return messageRepository.deleteById(id) >= COUNT_UPDATED_ROWS;
    }

    public MessageDto updateMessage(Message message) {
        if (messageRepository.updateById(message) < COUNT_UPDATED_ROWS){
            LOGGER.log(Level.WARNING, "Message {0} is not updated", message);
            return null;
        }
        return fromMessageToMessageDto(message);
    }

    private MessageDto fromMessageToMessageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .id_user(message.getId_user())
                .text(message.getText())
                .time_creation(message.getTime_creation())
                .build();
    }


}

