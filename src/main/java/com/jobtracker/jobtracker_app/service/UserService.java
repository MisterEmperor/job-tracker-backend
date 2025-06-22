package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.domain.JobApplication;
import com.jobtracker.jobtracker_app.domain.JobSearch;
import com.jobtracker.jobtracker_app.domain.User;
import com.jobtracker.jobtracker_app.model.UserDTO;
import com.jobtracker.jobtracker_app.repos.JobApplicationRepository;
import com.jobtracker.jobtracker_app.repos.JobSearchRepository;
import com.jobtracker.jobtracker_app.repos.UserRepository;
import com.jobtracker.jobtracker_app.util.NotFoundException;
import com.jobtracker.jobtracker_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobSearchRepository jobSearchRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public UserService(final UserRepository userRepository,
            final JobSearchRepository jobSearchRepository,
            final JobApplicationRepository jobApplicationRepository) {
        this.userRepository = userRepository;
        this.jobSearchRepository = jobSearchRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final JobSearch userJobSearch = jobSearchRepository.findFirstByUser(user);
        if (userJobSearch != null) {
            referencedWarning.setKey("user.jobSearch.user.referenced");
            referencedWarning.addParam(userJobSearch.getId());
            return referencedWarning;
        }
        final JobApplication userJobApplication = jobApplicationRepository.findFirstByUser(user);
        if (userJobApplication != null) {
            referencedWarning.setKey("user.jobApplication.user.referenced");
            referencedWarning.addParam(userJobApplication.getId());
            return referencedWarning;
        }
        return null;
    }

}
