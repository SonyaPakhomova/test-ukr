package com.example.testurk.shopTest.service;

public interface UserService {
    void updateName(String name, String email);

    void deleteAllInactive();
}
