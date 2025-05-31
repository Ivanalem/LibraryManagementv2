package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String formLogin(@RequestParam String username,
                            @RequestParam String password,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Успешная аутентификация
            // Здесь можно добавить кастомную логику, если нужно

            // Перенаправляем в зависимости от роли
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/api/v1/admin/adminDashboard";
            } else {
                auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));
                return "redirect:index";
            }

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";
        }
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Collections.singleton(User.Role.ROLE_USER));
        userRepository.save(user);
        return "redirect:/login";
    }

}
