package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.User;
import com.example.testurk.shopTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @RequestMapping(value = "/delete-inactive",method = RequestMethod.GET)
    public HttpStatus deleteAllInactive(){
        if (userService.deleteAllInactive() > 0) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public HttpStatus updateName(@RequestParam String name, @RequestParam String email) {
        if (userService.updateName(name, email) > 0) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/create")
    public User create(@RequestParam String name, @RequestParam String email, @RequestParam boolean status) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAccountStatus(status);
        return userService.create(user);
    }

    @GetMapping("/get-by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/get-by-name")
    public User getByName(@RequestParam String name) {
        return userService.getUserByName(name);
    }
}
