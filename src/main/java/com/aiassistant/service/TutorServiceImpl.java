package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
   
    private final ChatModel chatModel;

    @Override
    public AiResponse askFromAi(AiRequest aiRequest) {
//        System.out.println("Ask from AI");
        String response = chatModel.call(aiRequest.question());
        System.out.println("Response "+response);
        AiResponse aiResponse = new AiResponse(response);
        return aiResponse;
    }
}
