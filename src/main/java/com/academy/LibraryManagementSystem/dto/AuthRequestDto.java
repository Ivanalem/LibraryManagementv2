package com.academy.LibraryManagementSystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "users")
public class AuthRequestDto {
    @Column(name= "username")
    private String username;
    @Column(name="password")
    private String password;
}
