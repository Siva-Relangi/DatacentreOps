package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.NoteType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkOrderNoteResponseDTO {

    private Long noteId;
    private Long workOrderId;
    private Long authorId;
    private String noteText;
    private NoteType noteType;
    private LocalDateTime createdDate;
}