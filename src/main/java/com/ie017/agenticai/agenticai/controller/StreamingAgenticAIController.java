package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/streamingAgenticai")
public class StreamingAgenticAIController {

    private ChatClient chatClient;

    public StreamingAgenticAIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/stream/chat")
    public Flux<String> askLLmOnModeStreaming(String query) {
        return chatClient.prompt()
                .user(query)
                // Avec cette méthode, la réponse arrive immédiatement, en continu
                .stream()
                .content();
    }

    @GetMapping("/nostream/chat")
    public String askLLmOnModeBlock(String query) {
        return chatClient.prompt()
                .user(query)
                // Avec cette méthode, il faut attendre la réponse jusqu'à ce qu'elle arrive en un seul bloc
                .call()
                .content();
    }
}
