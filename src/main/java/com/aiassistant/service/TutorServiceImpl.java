package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class TutorServiceImpl implements TutorService {
   
    private ChatClient chatClient;

    public TutorServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public AiResponse askFromAi(AiRequest aiRequest) {
        String response = chatClient.prompt()
                .user(aiRequest.question())
                .system("Act as 10 years experience java teacher")
                .call()
                .content();
        System.out.println("Response: " + response);
        AiResponse aiResponse = new AiResponse(response);
        return aiResponse;
    }
}
