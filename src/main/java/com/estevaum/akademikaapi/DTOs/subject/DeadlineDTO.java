package com.estevaum.akademikaapi.DTOs.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DeadlineDTO(LocalDate date, String name) {
}
