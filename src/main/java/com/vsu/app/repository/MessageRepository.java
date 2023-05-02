package com.vsu.app.repository;

import com.vsu.app.entity.Message;

import com.vsu.app.exception.DBException;
import com.vsu.app.service.MessageService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class MessageRepository {
    private static final Logger LOGGER = Logger.getLogger(MessageRepository.class.getName());
    private static final String SELECT_BY_ID_QUERY =
            "SELECT * FROM public.message WHERE id_message = ?";

    private static final String INSERT_QUERY =
            "INSERT INTO public.message(" +
                    "id_profile, text_message) " +
                    "VALUES (?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE public.message " +
                    "SET id_profile=?, text_message=? " +
                    "WHERE id_message = ?";

    private static final String DELETE_BY_ID_QUERY =
            "DELETE FROM public.message " +
                    "WHERE id_message = ?";
    private final JdbcTemplate jdbcTemplate;

    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, (rs, rowNum) -> getMessageByResultSet(rs), id);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Message with id {0} is not selected", id);
            throw new DBException(e);
        }

    }

    public Message insert(Message message) {
        try {
            SimpleJdbcInsert insertContactList = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("public.message").usingColumns("id_profile", "text_message")
                    .usingGeneratedKeyColumns("id_message");

            Map<String,Object> insertParameters = new HashMap<>();
            insertParameters.put("id_profile", message.getId_user());
            insertParameters.put("text_message", message.getText());
            return getById((Long) insertContactList.executeAndReturnKey(insertParameters));
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Message {0} is not inserted", message);
            throw new DBException(e);
        }
    }

    public int updateById(Message message) {
        try {
            return jdbcTemplate.update(UPDATE_QUERY, message.getId_user(), message.getText(), message.getId());
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Message with id {0} is not updated", message.getId());
            throw new DBException(e);
        }
    }

    public int deleteById(Long id) {
        try {
            return jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Message with id {0} is not deleted", id);
            throw new DBException(e);
        }

    }

    private Message getMessageByResultSet(ResultSet rs) throws SQLException {
        return new Message(rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                rs.getTimestamp(4).toString());
    }
}