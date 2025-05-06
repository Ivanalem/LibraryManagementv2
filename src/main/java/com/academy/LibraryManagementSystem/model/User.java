package com.academy.LibraryManagementSystem.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
