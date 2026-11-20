package com.aiassistant.config;

import com.aiassistant.advisor.CustomAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {

    @Value("classpath:/prompts/system-prompt.st")
    private Resource systemPrompt;

    //Configured Jdbc Chat memory repo
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();

    }

    //Added defaultSystem
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, CustomAdvisor customAdvisor, ChatMemory chatMemory) {
        return builder
                .defaultSystem(systemPrompt)
                .defaultAdvisors(
                        customAdvisor,
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultOptions(
                        ChatOptions.builder()
                                .model("openai/gpt-oss-120b")
                                .temperature(0.3)
//                                .maxTokens(1000)
                )
        .build();
    }
}
