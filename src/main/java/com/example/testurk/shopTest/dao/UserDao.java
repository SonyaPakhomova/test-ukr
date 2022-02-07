package com.example.testurk.shopTest.dao;

public interface UserDao {
    void updateName(String name, String email);

    void deleteAllInactive();
}
