package com.ie017.agenticai.agenticai.controller;

import com.ie017.agenticai.agenticai.output.Permis;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/multimodalagenticai")
public class MultiModalAgenticAIController {

    private ChatClient chatClient;

    @Value("classpath:/images/permis.jpg")
    private Resource permis;

    public MultiModalAgenticAIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/askAboutIamge")
    public Permis describeImages(String query) {
        final String systemMessage = """
                Votre role est d'extraire les informations a partir l'image.
                """;
        return chatClient.prompt()
                .system(systemMessage)
                // .media pour ajoute un fichier a notre prompt
                .user(promptUserSpec -> promptUserSpec.text(query).media(MediaType.IMAGE_JPEG, permis))
                .call()
                .entity(Permis.class);
    }

    @PostMapping(value = "/askAboutFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String describeImages(String query, @RequestParam(name = "file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        final String systemMessage = """
                Votre role est d'extraire les informations a partir l'image.
                """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(promptUserSpec -> promptUserSpec.text(query).media(MediaType.IMAGE_JPEG, new ByteArrayResource(bytes)))
                .call()
                .content();
    }
}
