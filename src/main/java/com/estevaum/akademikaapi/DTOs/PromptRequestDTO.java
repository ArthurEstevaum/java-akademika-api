package com.estevaum.akademikaapi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record PromptRequestDTO(@NotBlank String prompt) {
}
