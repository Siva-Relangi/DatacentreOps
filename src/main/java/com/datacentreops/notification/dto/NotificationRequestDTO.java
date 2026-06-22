package com.datacentreops.notification.dto;
import com.datacentreops.notification.entity.NotificationCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {

    private Long userId;

    private String message;

    private NotificationCategory category;
}


