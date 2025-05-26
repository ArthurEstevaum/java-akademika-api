package com.estevaum.akademikaapi.DTOs;

import jakarta.validation.constraints.Email;

import javax.validation.constraints.NotNull;

public record LoginRequestDTO(@NotNull @Email String email, @NotNull String password) {
}
