package com.estevaum.akademikaapi.services;

import com.estevaum.akademikaapi.DTOs.RegisterUserRequestDTO;
import com.estevaum.akademikaapi.entities.User;
import com.estevaum.akademikaapi.exceptions.UserAlreadyExistsException;
import com.estevaum.akademikaapi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class RegisterUserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(@Valid RegisterUserRequestDTO requestData) throws UserAlreadyExistsException {
        Boolean userAlreadyExists = userRepository.existsByEmail(requestData.email());

        if(userAlreadyExists) {
            throw new UserAlreadyExistsException("Erro: o usuário já existe");
        }

        User user = new User(requestData.username(), passwordEncoder.encode(requestData.password()), requestData.email());

        return userRepository.save(user);
    }
}
