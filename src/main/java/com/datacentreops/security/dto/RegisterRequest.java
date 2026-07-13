package com.datacentreops.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Registration payload. Role is one of the platform roles (defaults to ColoCustomer). */
public record RegisterRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6, message = "password must be at least 6 characters") String password,
        String phone,
        Long organisationId,
        Long dataCentreId) {
}
