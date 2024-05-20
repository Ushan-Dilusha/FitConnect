package com.paf.fitConnect.service;

import com.paf.fitConnect.DTO.UserDTO;
import com.paf.fitConnect.modal.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<Object> createUser(User user);

    UserDTO getUserById(String userId);

    List<UserDTO> getAllUsers();

    ResponseEntity<Object> followUser(String userId, String followedUserId);

    ResponseEntity<Object> loginUser(String email, String password);

}
