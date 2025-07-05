package com.jobtracker.jobtracker_app.controllers;

import com.jobtracker.jobtracker_app.model.UserDTO;
import com.jobtracker.jobtracker_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;

    /**
     * Handles user registration requests.
     * This endpoint is publicly accessible (configured in SecurityConfig).
     * It expects a JSON payload representing the UserDTO.
     * @param userDTO DTO containing registration details (username, password, email).
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            authService.registerNewUser(userDTO);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return new ResponseEntity<>("An internal server error occurred during registration.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}