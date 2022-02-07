package com.example.testurk.shopTest.service;

import com.example.testurk.shopTest.model.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface UserService {
    void updateName(String name, String email);

    void deleteAllInactive();
}
