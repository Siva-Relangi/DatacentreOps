package com.datacentreops.iam.controller;

import com.datacentreops.iam.dto.*;
import com.datacentreops.iam.entity.User;
import com.datacentreops.iam.entity.Role;
import com.datacentreops.iam.entity.UserStatus;
import com.datacentreops.iam.mapper.UserMapper;
import com.datacentreops.iam.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iam/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {

        return UserMapper.toDTO(
                service.create(UserMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return service.findAll().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return UserMapper.toDTO(service.findById(id));
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto) {

        return UserMapper.toDTO(
                service.update(id, UserMapper.toEntity(dto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<UserResponseDTO> search(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) Long organisationId) {

        List<User> list;

        if (role != null) {
            list = service.findByRole(role);
        } else if (status != null) {
            list = service.findByStatus(status);
        } else if (organisationId != null) {
            list = service.findByOrganisation(organisationId);
        } else {
            list = service.findAll();
        }

        return list.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}