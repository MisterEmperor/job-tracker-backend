package com.jobtracker.jobtracker_app.domain.service;

import com.jobtracker.jobtracker_app.domain.model.User;
import com.jobtracker.jobtracker_app.api.dtos.user.UserDTO;
import com.jobtracker.jobtracker_app.domain.enums.UserRole;
import com.jobtracker.jobtracker_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setRole(UserRole.USER.toSpringSecurityRole());

        userRepository.save(user);
    }

}
