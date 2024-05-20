package com.paf.fitConnect.controller;

import com.paf.fitConnect.DTO.UserDTO;
import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/follow")
    public ResponseEntity<Object> followUser(@RequestParam String userId,
                                             @RequestParam String FollowedUserId) {
        return userService.followUser(userId,FollowedUserId);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

}
