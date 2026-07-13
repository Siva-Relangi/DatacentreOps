package com.datacentreops.iam.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.iam.entity.User;
import com.datacentreops.iam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //  CREATE
    public User create(User user) {

        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return repository.save(user);
    }

    //  GET ALL
    public List<User> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    //  FIND BY EMAIL
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    //Exists By Email
    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    //  UPDATE
    public User update(Long id, User u) {

        User existing = findById(id);

        existing.setName(u.getName());
        existing.setEmail(u.getEmail());
        existing.setRole(u.getRole());
        existing.setPhone(u.getPhone());
        existing.setOrganisationId(u.getOrganisationId());
        existing.setDataCentreId(u.getDataCentreId());
        existing.setStatus(u.getStatus());

        if (StringUtils.hasText(u.getPassword())) {
            existing.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        return repository.save(existing);
    }

    //  DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }

    //  SEARCH
    public List<User> findByRole(com.datacentreops.iam.entity.Role role) {
        return repository.findByRole(role);
    }

    public List<User> findByStatus(com.datacentreops.iam.entity.UserStatus status) {
        return repository.findByStatus(status);
    }

    public List<User> findByOrganisation(Long organisationId) {
        return repository.findByOrganisationId(organisationId);
    }
}