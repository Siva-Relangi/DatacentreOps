package com.datacentreops.notification.mapper;

import com.datacentreops.notification.dto.*;
import com.datacentreops.notification.entity.Notification;

public class NotificationMapper {

    public static Notification toEntity(NotificationRequestDTO dto) {

        Notification n = new Notification();

        n.setUserId(dto.getUserId());
        n.setMessage(dto.getMessage());
        n.setCategory(dto.getCategory());

        return n;
    }

    public static NotificationResponseDTO toDTO(Notification entity) {

        NotificationResponseDTO dto = new NotificationResponseDTO();

        dto.setNotificationId(entity.getNotificationId());
        dto.setUserId(entity.getUserId());
        dto.setMessage(entity.getMessage());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}