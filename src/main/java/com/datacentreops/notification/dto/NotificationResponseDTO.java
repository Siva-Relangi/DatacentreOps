package com.datacentreops.notification.dto;

import com.datacentreops.notification.entity.NotificationCategory;
import com.datacentreops.notification.entity.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponseDTO {

    private Long notificationId;
    private Long userId;
    private String message;
    private NotificationCategory category;
    private NotificationStatus status;
    private LocalDateTime createdDate;
}