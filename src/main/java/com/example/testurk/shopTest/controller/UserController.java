package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.User;
import com.example.testurk.shopTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @DeleteMapping("/delete-inactive")
    public ResponseEntity<?> deleteAllInactive() {
        ResponseEntity<?> responseEntity;
        try {
            userService.deleteAllInactive();
            responseEntity = new ResponseEntity<String>("Users Deleted", HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<String>("Unable delete movie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PatchMapping("/update-by-email")
    public ResponseEntity<String> updateName(@RequestParam String name, @RequestParam String email) {
        userService.updateName(name, email);
        return ResponseEntity.ok("Entity update");
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
