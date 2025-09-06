package com.ie017.agenticai.agenticai.service;

import reactor.core.publisher.Flux;

public interface AgenticAIService {
    Flux<String> onQuery(String query);
}
