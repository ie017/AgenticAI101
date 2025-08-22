package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agenticai")
public class AgenticAIController {
    // Interface qui vous permet de communiquer avec n'import quel LLM (openai, ollama, ...)
    private ChatClient chatClient;

    public AgenticAIController(ChatClient.Builder builder, ChatMemory chatMemory) {
        // Avec le pattern builder on peut ajouter des configurations a notre interface chatClient
        this.chatClient = builder
                // Je demmande a utiliser ces'intercepteurs a chaque fois le client envoi une requete
                .defaultAdvisors(new SimpleLoggerAdvisor())
                //  ChatMemory est l'interface qui nous donne la mémoire par défaut de Spring AI avec un max des messages = 20, ce qui nous permet de régler le comportement de notre agent IA (agent ai are stateless by default)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
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
