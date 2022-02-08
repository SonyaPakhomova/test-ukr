package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.UserDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private AppConfig appConfig;

    @Override
    public int updateName(String name, String email) {
        String query = "UPDATE users SET name = ? WHERE email = ?";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement updateUserStatement =
                     connection.prepareStatement(query)) {
            updateUserStatement.setString(1, name);
            updateUserStatement.setString(2, email);
            return updateUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update name for user with email " + email, e);
        }
    }

    @Override
    public int deleteAllInactive() {
        String query = "DELETE FROM users WHERE account_status = false";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement deleteUserStatement
                     = connection.prepareStatement(query)) {
            return deleteUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete inactive user : ", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllGoodsStatement
                     = connection.prepareStatement(query)) {
            List<User> users = new ArrayList<>();
            resultSet = getAllGoodsStatement.executeQuery();
            while (resultSet.next()) {
                users.add(setUsers(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of users "
                    + "from users table. ", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, email, account_status)"
                + "VALUES (?, ?, ?)";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement createUserStatement =
                     connection.prepareStatement(
                             query, Statement.RETURN_GENERATED_KEYS)) {
            createUserStatement.setObject(1, user.getName());
            createUserStatement.setObject(2, user.getEmail());
            createUserStatement.setObject(3, user.getAccountStatus());
            createUserStatement.executeUpdate();
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user " + user, e);
        }
        return user;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setString(1, email);
            ResultSet resultSet = getUserStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = setUsers(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by email " + email, e);
        }
    }

    @Override
    public Optional<User> getUserByName(String name) {
        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setString(1, name);
            ResultSet resultSet = getUserStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = setUsers(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by name " + name, e);
        }
    }

    private User setUsers(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Boolean account_status = resultSet.getBoolean("account_status");
        User user = new User();
        user.setAccountStatus(account_status);
        user.setName(name);
        user.setEmail(email);
        user.setId(userId);
        return user;
    }


}
