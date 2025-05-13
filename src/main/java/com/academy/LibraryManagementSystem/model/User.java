package com.academy.LibraryManagementSystem.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Integer status;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
