package com.aiassistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
                .defaultOptions(
                        ChatOptions.builder()
                                .model("openai/gpt-oss-120b")
                                .temperature(0.3)
                                .maxTokens(1000)
                )
        .build();
    }
}
