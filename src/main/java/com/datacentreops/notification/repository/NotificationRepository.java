package com.datacentreops.notification.repository;

import com.datacentreops.notification.entity.Notification;
import com.datacentreops.notification.entity.NotificationCategory;
import com.datacentreops.notification.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);

    List<Notification> findByCategory(NotificationCategory category);
}