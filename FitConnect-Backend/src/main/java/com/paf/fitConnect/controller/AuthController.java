package com.paf.fitConnect.controller;

import com.paf.fitConnect.modal.RegistrationSource;
import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public void home(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {
        // Log the principal for debugging purposes
        System.out.println("/looooo" + principal);
        getUsername(principal);

        // Redirect to the frontend application
        response.sendRedirect("http://localhost:3000");
    }

    @GetMapping("api/user")
    @ResponseBody
    public ResponseEntity<Object> getUsername(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // Extract user details from OAuth2 principal
            String name = principal.getAttribute("name");
            String email = principal.getAttribute("email");
            String picture = principal.getAttribute("picture");

            // Create a new User object with the extracted details
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setProfileImage(picture);
            user.setSource(RegistrationSource.GOOGLE);

            // Create or update the user in the database using the userService
            return userService.createUser(user);
        } else {
            // Return 401 Unauthorized if the principal is null
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
