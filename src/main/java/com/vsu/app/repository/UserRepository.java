package com.vsu.app.repository;

import com.vsu.app.entity.User;
import com.vsu.app.exception.DBException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

    private static final String SELECT_BY_ID_QUERY =
            "SELECT * FROM profile WHERE id_user = ?";

    private static final String SELECT_BY_EMAIL_QUERY =
            "SELECT * FROM profile WHERE email_user = ?";

    private static final String INSERT_QUERY =
            "INSERT INTO profile(" +
                    "surname_user, name_user, birthday_user, phone_user, email_user, password_user) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String DELETE_QUERY_BY_ID =
            "DELETE FROM profile WHERE id_user = ?";

    private static final String UPDATE_QUERY =
            "UPDATE profile " +
                    "SET surname_user=?, name_user=?, birthday_user=?, phone_user=?, email_user=?, password_user=? " +
                    "WHERE id_user = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, (rs, rowNum) -> getUserByResultSet(rs), id);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "User with id {0} is not selected", id);
            throw new DBException(e);
        }

    }

    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, (rs, rowNum) -> getUserByResultSet(rs), email);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "User with email {0} is not selected", email);
            throw new DBException(e);
        }
    }

    public int insert(User user) {
        try {
            return jdbcTemplate.update(INSERT_QUERY, user.getSurname(), user.getName(), Date.valueOf(user.getBirthday()), user.getPhone(), user.getEmail(), user.getPassword());
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "User with email {0} is not inserted", user.getEmail());
            throw new DBException(e);
        }
    }

    public int updateById(User user) {
        try {
            return jdbcTemplate.update(UPDATE_QUERY, user.getSurname(), user.getName(), Date.valueOf(user.getBirthday()), user.getPhone(), user.getEmail(), user.getPassword(), user.getId());
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "User with id {0} is not updated", user.getId());
            throw new DBException(e);
        }
    }

    public int deleteById(Long id) {
        try {
            return jdbcTemplate.update(DELETE_QUERY_BY_ID, id);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "User with id {0} is not deleted", id);
            throw new DBException(e);
        }

    }

    private User getUserByResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDate(4).toString(),
                rs.getString(6),
                rs.getString(5),
                rs.getString(7));
    }

}
