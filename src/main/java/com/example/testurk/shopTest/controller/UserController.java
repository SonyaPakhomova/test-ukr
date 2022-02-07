package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @DeleteMapping("/delete-inactive")
    public ResponseEntity<String> deleteAllInactive() {
        userService.deleteAllInactive();
        return ResponseEntity.ok("Entity deleted");
    }

    @PatchMapping("/update-by-email")
    public ResponseEntity<String> updateName(@RequestParam String name, @RequestParam String email){
        userService.updateName(name, email);
        return ResponseEntity.ok("Entity update");
    }
}
