package com.datacentreops.workorder.dto;

import com.datacentreops.workorder.entity.NoteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderNoteRequestDTO {

    private Long workOrderId;
    private Long authorId;
    private String noteText;
    private NoteType noteType;
}