package com.aiassistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {

    @Value("classpath:/prompts/system-prompt.st")
    private Resource systemPrompt;

    //Added defaultSystem
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
                .defaultSystem(systemPrompt)
                .defaultOptions(
                        ChatOptions.builder()
                                .model("openai/gpt-oss-120b")
                                .temperature(0.3)
//                                .maxTokens(1000)
                )
        .build();
    }
}
