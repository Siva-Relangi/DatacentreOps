package com.datacentreops.notification.service;

import com.datacentreops.common.ResourceNotFoundException;
import com.datacentreops.iam.repository.UserRepository;
import com.datacentreops.notification.entity.*;
import com.datacentreops.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository repository,
                               UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //  GET ALL
    public List<Notification> findAll() {
        return repository.findAll();
    }

    //  GET BY ID
    public Notification findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }

    //  DELETE
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    //  USER FETCH
    public List<Notification> findByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Notification> findUnreadByUser(Long userId) {
        return repository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }

    //  ACTIONS (IMPORTANT)
    public Notification markRead(Long id) {

        Notification n = findById(id);
        n.setStatus(NotificationStatus.READ);

        return repository.save(n);
    }

    public Notification dismiss(Long id) {

        Notification n = findById(id);
        n.setStatus(NotificationStatus.DISMISSED);

        return repository.save(n);
    }
}