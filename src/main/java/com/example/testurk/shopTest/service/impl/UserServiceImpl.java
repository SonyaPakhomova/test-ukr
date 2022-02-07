package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.UserDao;
import com.example.testurk.shopTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public void updateName(String name, String email) {
        userDao.updateName(name, email);
    }

    @Override
    public void deleteAllInactive() {
        userDao.deleteAllInactive();
    }
}
