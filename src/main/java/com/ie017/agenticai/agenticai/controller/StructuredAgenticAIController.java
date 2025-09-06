package com.ie017.agenticai.agenticai.controller;

import com.ie017.agenticai.agenticai.output.StructuredData;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/structuredAgenticai")
public class StructuredAgenticAIController {

    private ChatClient chatClient;

    public StructuredAgenticAIController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public StructuredData askLLm(String query) {
        String systemMessage = """
                Vous êtes un spécialiste dans le domaine IT, particulièrement en Java.
                Respond ONLY in JSON with fields: definition, benefits, useCase."
                """;
        return this.chatClient.prompt()
                .system(systemMessage)
                .user(query)
                .call()
                // signifie que je veux un résultat au format spécifié dans entity
                .entity(StructuredData.class);
    }
}
