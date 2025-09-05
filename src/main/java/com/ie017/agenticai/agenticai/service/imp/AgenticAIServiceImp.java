package com.ie017.agenticai.agenticai.service.imp;

import com.ie017.agenticai.agenticai.service.AgenticAIService;
import com.ie017.agenticai.agenticai.tools.AgentTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class AgenticAIServiceImp implements AgenticAIService {

    private ChatClient chatClient;

    public AgenticAIServiceImp(ChatClient.Builder chatClient, ChatMemory chatMemory, AgentTools agentTools) {
        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                // Pour associer notre agent avec les outils, après cette étape, notre agent est capable de scanner toutes les méthodes qui sont annotées avec @Tools pour récupérer les informations
                .defaultTools(agentTools)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> onQuery(String query) {
        return this.chatClient.prompt()
                .user(query)
                .stream()
                .content();
    }
}
