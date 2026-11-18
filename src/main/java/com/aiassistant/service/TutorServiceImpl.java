package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import com.aiassistant.dto.StudyPlan;
import com.aiassistant.dto.StudyPlanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
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

    @Value("classpath:/prompts/study-prompt.st")
    private Resource studyPlanPrompt;
   
    private final ChatClient chatClient;

    @Override
    public AiResponse askFromAi(AiRequest aiRequest) {

        String topic = aiRequest.topic();
        String level = aiRequest.level();
        String language = aiRequest.language();


       //Templating using chatClient
        String response = chatClient.prompt()
                .options(
                        ChatOptions.builder().maxTokens(1000)
                )
                .user(user->user
                        .text(queryResource)
                        .param("topic",topic)
                        .param("level",level)
                        .param("language",language))
                .call()
                .content();
        System.out.println("Response: " + response);
        AiResponse aiResponse = new AiResponse(response);
        return aiResponse;
    }

    @Override
    public StudyPlan prepareStudyPlan(StudyPlanRequest request) {
        StudyPlan entity = chatClient.prompt()
                .user(user->user.text(studyPlanPrompt)
                        .param("topic",request.topic())
                        .param("level",request.level())
                        .param("days",request.days()))
                .call()
                .entity(StudyPlan.class);
        return entity;
    }
}
