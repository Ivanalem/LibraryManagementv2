package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.dto.JwtRequestDto;
import com.academy.LibraryManagementSystem.dto.JwtResponseDto;
import com.academy.LibraryManagementSystem.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/auth")
    public JwtResponseDto auth(@RequestBody JwtRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(),
                        request.getPassword()));

        String token = jwtUtils.generateToken(request.getUserName());

        return new JwtResponseDto(token);
    }
}
