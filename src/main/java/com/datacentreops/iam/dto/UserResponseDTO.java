package com.datacentreops.iam.dto;

import com.datacentreops.iam.entity.Role;
import com.datacentreops.iam.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private Role role;
    private UserStatus status;
    private String phone;
    private Long organisationId;
    private Long dataCentreId;
}