package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/ZeroShotPromptingChat")
    public String askLLmBasedOnZeroShotPromptingChat(String query) {
        return chatClient.prompt()// prompt utiliser pour la creation d'un prompt avec ces composants (system message, user message, exemple message)
                // Ensemble des instructions que vous donnez dans le prompt, et qui le LLM doit respecter pour repondre a la query (question)
                .system("Use uppercase in every response, dont forget you are a software developer")
                .user(query) // user message
                .call() // Pour avoir la reponse
                .content(); // retourner le resultat de la reponse
    }

    @GetMapping("/FewShotPromptingChat")
    public String askLLmBasedOnFewShotPromptingChat(String query) {
        List<Message> examples = List.of(
                new UserMessage("what is the github account of ie017?"),
                new AssistantMessage("Voila take this link : https://github.com/ie017")
        );
        return chatClient.prompt()
                .system("Use uppercase in every response, dont forget you are a software developer")
                .messages(examples)
                .user(query)
                .call()
                .content();
    }


}
