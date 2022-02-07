package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.UserDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private AppConfig appConfig;

    @Override
    public void updateName(String name, String email) {
        String query = "UPDATE users SET name = ? WHERE email = ?";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement updateUserStatement =
                     connection.prepareStatement(query)) {
            updateUserStatement.setString(1, name);
            updateUserStatement.setString(2, email);
            updateUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update name for user with email " + email, e);
        }
    }

    @Override
    public void deleteAllInactive() {
        String query = "DELETE FROM users WHERE account_status = false";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getUserStatement
                     = connection.prepareStatement(query)) {
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete inactive user : ", e);
        }
    }
}
