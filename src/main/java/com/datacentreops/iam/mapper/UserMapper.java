package com.datacentreops.iam.mapper;

import com.datacentreops.iam.dto.*;
import com.datacentreops.iam.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {

        User u = new User();

        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setRole(dto.getRole());
        u.setPhone(dto.getPhone());
        u.setOrganisationId(dto.getOrganisationId());
        u.setDataCentreId(dto.getDataCentreId());

        return u;
    }

    public static UserResponseDTO toDTO(User entity) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setPhone(entity.getPhone());
        dto.setOrganisationId(entity.getOrganisationId());
        dto.setDataCentreId(entity.getDataCentreId());

        return dto;
    }
}