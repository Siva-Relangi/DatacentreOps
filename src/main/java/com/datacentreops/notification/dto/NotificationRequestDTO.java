package com.datacentreops.notification.dto;
import com.datacentreops.notification.entity.NotificationCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {

    @NotNull(message = "User is required")
    private Long userId;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Category is required")
    private NotificationCategory category;
}


