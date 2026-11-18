package com.aiassistant.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAdvisor implements CallAdvisor {

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        //Pre processing
        long startTime = System.currentTimeMillis();
        log.info("AI request started");

        //call to next advisor or chatModel
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        //Post Processing
        long endTime = System.currentTimeMillis();
        long totalDuration = endTime - startTime;

        log.info("Duration of llm call {} ms",totalDuration);

        log.info("AI request finished");

        //Meta data ko bhi process kar sakte hai
        Usage usage = chatClientResponse.chatResponse().getMetadata().getUsage();
        log.info(
                "promptTokens={}, completionTokens={}, totalTokens={}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens()
        );
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "CustomAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
