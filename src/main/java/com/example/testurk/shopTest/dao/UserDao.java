package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void updateName(String name, String email);

    int deleteAllInactive();

    List<User> getAll();

    User create(User user);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByName(String name);
}
