package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgenticAIController {
    // Interface qui vous permet de communiquer avec n'import quel LLM (openai, ollama, ...)
    private ChatClient chatClient;

    public AgenticAIController(ChatClient.Builder builder) {
        // Avec le pattern builder on peut ajouter des configurations a notre interface chatClient
        this.chatClient = builder
                .build();
    }


}
