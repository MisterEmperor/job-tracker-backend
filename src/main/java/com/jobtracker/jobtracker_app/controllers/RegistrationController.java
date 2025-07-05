package com.jobtracker.jobtracker_app.controllers;

import com.jobtracker.jobtracker_app.dto.MessageResponse;
import com.jobtracker.jobtracker_app.model.UserDTO;
import com.jobtracker.jobtracker_app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
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
    @PostMapping("/users")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            authService.registerNewUser(userDTO);
            return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return new ResponseEntity<>(new MessageResponse("An internal server error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}