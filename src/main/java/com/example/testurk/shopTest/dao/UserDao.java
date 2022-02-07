package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.User;

import java.util.List;

public interface UserDao {
    void updateName(String name, String email);

    void deleteAllInactive();
}
