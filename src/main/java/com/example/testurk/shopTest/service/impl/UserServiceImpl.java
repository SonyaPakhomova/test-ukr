package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.UserDao;
import com.example.testurk.shopTest.model.User;
import com.example.testurk.shopTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public int updateName(String name, String email) {
        return userDao.updateName(name, email);
    }

    @Override
    public int deleteAllInactive() {
        return userDao.deleteAllInactive();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Can't get user by email " + email));
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name)
                .orElseThrow(() -> new RuntimeException("Can't get user by name " + name));
    }
}
