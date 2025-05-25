package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.dto.JwtRequestDto;
import com.academy.LibraryManagementSystem.dto.JwtResponseDto;
import com.academy.LibraryManagementSystem.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/auth")
    public JwtResponseDto auth(@RequestBody
                               JwtRequestDto requestDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        String token = jwtUtils.generateToken(requestDto.getUsername());

        return new JwtResponseDto(token);
    }
}
