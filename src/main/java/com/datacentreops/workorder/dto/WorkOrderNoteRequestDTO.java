package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.NoteType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderNoteRequestDTO {

    @NotNull(message = "Work Order is required")
    private Long workOrderId;

    private Long authorId;

    @NotBlank(message = "Note is required")
    private String noteText;

    @NotNull(message = "Note Type is required")
    private NoteType noteType;
}