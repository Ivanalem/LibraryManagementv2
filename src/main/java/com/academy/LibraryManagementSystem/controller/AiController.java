package com.academy.LibraryManagementSystem.controller;

import com.academy.LibraryManagementSystem.dto.AiRequestDTO;
import com.academy.LibraryManagementSystem.dto.AiResponseDTO;
import com.academy.LibraryManagementSystem.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/recommend")
    public AiResponseDTO recommend(@RequestBody AiRequestDTO request) {

        String answer = aiService.askAi(request.prompt());

        return new AiResponseDTO(answer);
    }
}
