package com.datacentreops.iam.dto;

import com.datacentreops.iam.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
    private String phone;
    private Long organisationId;
    private Long dataCentreId;
}