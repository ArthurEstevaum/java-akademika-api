package com.estevaum.akademikaapi.controllers;

import com.estevaum.akademikaapi.DTOs.LoginRequestDTO;
import com.estevaum.akademikaapi.DTOs.LoginResponseDTO;
import com.estevaum.akademikaapi.DTOs.RegisterUserRequestDTO;
import com.estevaum.akademikaapi.entities.User;
import com.estevaum.akademikaapi.services.AuthenticationService;
import com.estevaum.akademikaapi.services.RegisterUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationService authService;
    private final RegisterUserService registerService;

    public AuthenticationController(AuthenticationService authService, RegisterUserService registerService) {
        this.authService = authService;
        this.registerService = registerService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestData) throws Exception {
        String accessToken = authService.createAccessToken(requestData.email(), requestData.password());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, requestData.email()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterUserRequestDTO requestData) throws Exception {
        User user = registerService.register(requestData);
        String accessToken = authService.createAccessToken(requestData.email(), requestData.password());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, requestData.email()));
    }
}
