package com.example.testurk.shopTest.service;

import com.example.testurk.shopTest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void updateName(String name, String email);

    int deleteAllInactive();

    List<User> getAll();

    User create(User user);

    User getUserByEmail(String email);

    User getUserByName(String name);
}
