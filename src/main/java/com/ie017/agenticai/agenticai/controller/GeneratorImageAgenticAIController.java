package com.ie017.agenticai.agenticai.controller;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generatorimageagenticai")
public class GeneratorImageAgenticAIController {

    private OpenAiImageModel openAiImageModel;

    public GeneratorImageAgenticAIController(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("/generate")
    public String generateImages(String query) {
        final ImageOptions imageOptions = OpenAiImageOptions.builder()
                .quality("hd")
                // le modele utiliser pour la generation des images dans open ai.
                .model("dall-e-3")
                .build();
        return openAiImageModel.call(new ImagePrompt(query, imageOptions)).getResult().getOutput().getUrl();
    }
}
