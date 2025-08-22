package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agenticai")
public class AgenticAIController {
    // Interface qui vous permet de communiquer avec n'import quel LLM (openai, ollama, ...)
    private ChatClient chatClient;

    public AgenticAIController(ChatClient.Builder builder) {
        // Avec le pattern builder on peut ajouter des configurations a notre interface chatClient
        this.chatClient = builder
                // Je demmande a utiliser c'intercepteur a chaque fois le client envoi un requete
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat")
    public String askLLm(String query) {
        return chatClient.prompt()// prompt utiliser pour la creation d'un prompt avec ces composants (system message, user message, exemple message)
                .user(query) // user message
                .call() // Pour avoir la reponse
                .content(); // retourner le resultat de la reponse
    }


}
