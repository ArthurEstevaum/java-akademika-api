package com.estevaum.akademikaapi.DTOs.subject;

import com.estevaum.akademikaapi.enums.Days;
import com.estevaum.akademikaapi.enums.Status;

import java.util.List;

public record SubjectResponseDTO(Long id, String name, Short quarter, Status status, String syllabus, String teacher, List<Days> days, List<DeadlineDTO> deadlines) {
}
