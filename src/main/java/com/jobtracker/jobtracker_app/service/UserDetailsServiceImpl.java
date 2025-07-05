package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * This is the only method you need to implement.
     * Spring Security calls this method when it needs to load a user's details,
     * typically during a login attempt.
     * @param username the username provided by the user trying to authenticate.
     * @return a UserDetails object (which your User entity now implements).
     * @throws UsernameNotFoundException if a user with the given username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}