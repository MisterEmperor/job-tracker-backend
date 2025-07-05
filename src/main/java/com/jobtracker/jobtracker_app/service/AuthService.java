package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.domain.User;
import com.jobtracker.jobtracker_app.model.UserDTO;
import com.jobtracker.jobtracker_app.model.UserRole;
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
