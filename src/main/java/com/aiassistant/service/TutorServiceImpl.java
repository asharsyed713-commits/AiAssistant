package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import com.aiassistant.dto.ResponseLanguage;
import com.aiassistant.dto.StudentLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
   
    private final ChatClient chatClient;

    @Override
    public AiResponse askFromAi(AiRequest aiRequest) {

        String topic = aiRequest.topic();
        String level = aiRequest.level();
        String language = aiRequest.language();

        String prompt = """
                Explain %s,
                Student level %s.
                response in %s language.
                Explain using example and code in 70 words.
                """.formatted(
                        topic,
                level,
                language
        );

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println("Response: " + response);
        AiResponse aiResponse = new AiResponse(response);
        return aiResponse;
    }
}
