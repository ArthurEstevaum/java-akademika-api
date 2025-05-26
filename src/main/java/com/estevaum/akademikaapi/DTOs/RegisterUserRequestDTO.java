package com.estevaum.akademikaapi.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequestDTO(@NotNull String username, @NotNull String password, @Email String email) {
}