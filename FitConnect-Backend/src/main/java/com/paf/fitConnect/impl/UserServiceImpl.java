package com.paf.fitConnect.impl;

import com.paf.fitConnect.DTO.UserDTO;
import com.paf.fitConnect.DTO.UserResDTO;
import com.paf.fitConnect.modal.RegistrationSource;
import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> createUser(User user) {
        if(user.getSource() == null){
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setFollowedUsers(new ArrayList<>());
            user.setSource(RegistrationSource.CREDENTIAL);
            User savedUser = userRepository.save(user);
            UserDTO savedUserDTO = new UserDTO();
            BeanUtils.copyProperties(savedUser, savedUserDTO);
            return new ResponseEntity<>("Register Successfully", HttpStatus.OK);
        }

        if(Objects.equals(user.getSource(), RegistrationSource.GOOGLE)){
            String email = user.getEmail();
            if (userRepository.existsByEmail(email)) {
                User googleUser = userRepository.findByEmail(email);
                UserResDTO userDto = new UserResDTO();
                BeanUtils.copyProperties(googleUser, userDto);
                return  new ResponseEntity<>(userDto, HttpStatus.OK);
            }

            User googleUser = new User();
            googleUser.setName(user.getName());
            googleUser.setEmail(user.getEmail());
            googleUser.setProfileImage(user.getProfileImage());
            googleUser.setSource(RegistrationSource.GOOGLE);
            try {
                userRepository.save(googleUser);
                UserResDTO userDto = new UserResDTO();
                BeanUtils.copyProperties(googleUser, userDto);
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }

    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(optionalUser.get(), userDTO);
            return userDTO;
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public ResponseEntity<Object> followUser(String userId, String followedUserId) {
        try {
            User user= userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
            User followUser = userRepository.findById(followedUserId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + followedUserId));
            if (user.getFollowedUsers() == null) {
                user.setFollowedUsers(new ArrayList<>());
            }
            if (followUser.getFollowingUsers() == null) {
                followUser.setFollowingUsers(new ArrayList<>());
            }
            if (user.getFollowedUsers().contains(followedUserId)) {
                user.getFollowedUsers().remove(followedUserId);
                followUser.getFollowingUsers().remove(userId);
                user.setFollowersCount(user.getFollowersCount() - 1);
                followUser.setFollowingCount(followUser.getFollowingCount() -1);
            } else {
                user.getFollowedUsers().add(followedUserId);
                followUser.getFollowingUsers().add(userId);
                user.setFollowersCount(user.getFollowersCount() + 1);
                followUser.setFollowingCount(followUser.getFollowingCount() + 1);
            }
            userRepository.save(user);
            userRepository.save(followUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<Object> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("Invalid password or email", HttpStatus.UNAUTHORIZED);
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            UserResDTO userDto = new UserResDTO();
            BeanUtils.copyProperties(user, userDto);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid password or email", HttpStatus.UNAUTHORIZED);
        }
    }

}
