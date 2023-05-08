package com.vsu.app.service;

import com.vsu.app.entity.Message;
import com.vsu.app.exception.ValidationException;
import com.vsu.app.repository.MessageRepository;
import com.vsu.app.response.MessageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public int deleteMessage(Long idMessage, Long idUser) {
        Message message = messageRepository.getById(idMessage);
        validateMessageForModify(idMessage, idUser, message);
        return messageRepository.deleteById(idMessage);
    }

    @Transactional
    public MessageDto updateMessage(Long idMessage, Long idUser) {
        Message message = messageRepository.getById(idMessage);
        validateMessageForModify(idMessage, idUser, message);
        messageRepository.updateById(message);
        return fromMessageToMessageDto(message);
    }

    private static void validateMessageForModify(Long idMessage, Long idUser, Message message) {
        if (!message.getIdUser().equals(idUser)) {
            LOGGER.log(Level.WARNING, String.format("User with id {0} did not send message with id {1}", idUser, idMessage));
            throw new ValidationException("User ids do not match");
        }
    }

    private MessageDto fromMessageToMessageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .idUser(message.getIdUser())
                .text(message.getText())
                .timeCreation(message.getTimeCreation())
                .build();
    }
}

