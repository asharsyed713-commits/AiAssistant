package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    @Value("classpath:/prompts/java-teacher-prompt.st")
    private Resource queryResource;
   
    private final ChatClient chatClient;

    @Override
    public AiResponse askFromAi(AiRequest aiRequest) {

        String topic = aiRequest.topic();
        String level = aiRequest.level();
        String language = aiRequest.language();

        //Added prompt template
        PromptTemplate template = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .resource(queryResource)
                .variables(Map.of(
                        "topic",topic,
                        "level",level,
                        "language",language
                )).build();


        String prompt = template.render();

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println("Response: " + response);
        AiResponse aiResponse = new AiResponse(response);
        return aiResponse;
    }
}
