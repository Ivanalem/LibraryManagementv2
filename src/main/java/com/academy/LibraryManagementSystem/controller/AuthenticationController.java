package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.model.User;
import com.academy.LibraryManagementSystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

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
                            HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Перенаправляем на главную или в админку в зависимости от роли
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/api/v1/admin/adminDashboard";
            } else {
                return "redirect:index";
            }

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login"; // Возврат на страницу логина с ошибкой
        }
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Set.of(User.Role.ROLE_USER));
            userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
        model.addAttribute("error", "Пользователь с таким email уже зарегистрирован.");
        }
        return "redirect:/login";
    }

}
