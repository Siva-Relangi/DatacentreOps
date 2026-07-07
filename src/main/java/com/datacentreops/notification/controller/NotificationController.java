package com.datacentreops.notification.controller;

import com.datacentreops.notification.dto.*;
import com.datacentreops.notification.entity.*;
import com.datacentreops.notification.mapper.NotificationMapper;
import com.datacentreops.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponseDTO create(@Valid  @RequestBody NotificationRequestDTO dto) {

        return NotificationMapper.toDTO(
                service.create(NotificationMapper.toEntity(dto))
        );
    }

    @GetMapping
    public List<NotificationResponseDTO> getAll() {

        return service.findAll()
                .stream()
                .map(NotificationMapper::toDTO)
                .toList();
    }

    @GetMapping("/by-user/{userId}")
    public List<NotificationResponseDTO> byUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "false") boolean unreadOnly) {

        List<Notification> list = unreadOnly
                ? service.findUnreadByUser(userId)
                : service.findByUser(userId);

        return list.stream()
                .map(NotificationMapper::toDTO)
                .toList();
    }

    @PatchMapping("/{id}/read")
    public NotificationResponseDTO markRead(@PathVariable Long id) {

        return NotificationMapper.toDTO(service.markRead(id));
    }

    @PatchMapping("/{id}/dismiss")
    public NotificationResponseDTO dismiss(@PathVariable Long id) {

        return NotificationMapper.toDTO(service.dismiss(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}