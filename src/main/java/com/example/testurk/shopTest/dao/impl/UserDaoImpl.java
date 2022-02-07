package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.dao.UserDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.util.ConnectionUtil;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void updateName(String name, String email) {
        String selectQuery = "UPDATE users SET name = ? WHERE email = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateCarStatement =
                     connection.prepareStatement(selectQuery)) {
            updateCarStatement.setString(1, name);
            updateCarStatement.setString(2, email);
            updateCarStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update name for user with email " + email, e);
        }
    }

    @Override
    public void deleteAllInactive() {
        String selectQuery = "DELETE FROM user WHERE account_status = false";
        ResultSet resultSet;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getUserStatement
                     = connection.prepareStatement(selectQuery)) {
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete inactive user : ", e);
        }
    }
}
