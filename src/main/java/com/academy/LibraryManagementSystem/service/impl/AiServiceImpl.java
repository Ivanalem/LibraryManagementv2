package com.academy.LibraryManagementSystem.service.impl;

import com.academy.LibraryManagementSystem.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public AiServiceImpl(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String askAi(String prompt) {

        Map<String, Object> body = Map.of(
                "model", "openai/gpt-oss-20b:free",

                "messages", List.of(

                        Map.of(
                                "role", "system",
                                "content", """
                                        Ты AI библиотекарь для онлайн библиотеки.
                                        Отвечай кратко.
                                        Рекомендуй максимум 3 книги.
                                        Не используй таблицы.
                                        Не пиши длинные объяснения.
                                        """
                        ),

                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),

                "max_tokens", 150,

                "temperature", 0.6
        );
        try {
            Map response = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.get("choices");

            if (choices == null || choices.isEmpty()) {
                return "AI не вернул ответ.";
            }

            Map<String, Object> choice = choices.get(0);

            Map<String, Object> message =
                    (Map<String, Object>) choice.get("message");

            if (message == null || message.get("content") == null) {
                return "AI ответ пустой.";
            }

            return message.get("content").toString();
        } catch (Exception e) {
            return "Ошибка AI сервиса";
        }
    }
}
