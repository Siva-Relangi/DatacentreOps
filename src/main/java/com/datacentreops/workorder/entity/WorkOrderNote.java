package com.datacentreops.workorder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_order_note")
@Getter
@Setter
@NoArgsConstructor
public class WorkOrderNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column(nullable = false)
    private Long workOrderId;

    private Long authorId;

    private Long incidentId;

    @Column(nullable = false, length = 2000)
    private String noteText;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    private LocalDateTime createdDate = LocalDateTime.now();
}