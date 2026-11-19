
package com.aiassistant.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class CustomAdvisor implements CallAdvisor, StreamAdvisor {

    // 1. Normal synchronous AI call
    @Override
    public ChatClientResponse adviseCall(
            ChatClientRequest chatClientRequest,
            CallAdvisorChain callAdvisorChain) {

        log.info("Normal request call started");

        long startTime = System.currentTimeMillis();

        try {
            // Pass request to the next advisor or ChatModel
            ChatClientResponse response =
                    callAdvisorChain.nextCall(chatClientRequest);

            // Calculate execution time
            long duration = System.currentTimeMillis() - startTime;

            log.info("AI request finished in {} ms", duration);

            // Extract token usage
            if (response.chatResponse() != null) {

                Usage usage = response.chatResponse()
                        .getMetadata()
                        .getUsage();

                logUsage(usage);
            }

            return response;

        } catch (Exception e) {

            long duration = System.currentTimeMillis() - startTime;

            log.error("AI request failed after {} ms", duration, e);

            throw e;
        }
    }

    // 2. Advisor name
    @Override
    public String getName() {
        return "CustomAdvisor";
    }

    // 3. Advisor execution order
    @Override
    public int getOrder() {
        return 0;
    }

    // 4. Streaming AI call
    @Override
    public Flux<ChatClientResponse> adviseStream(
            ChatClientRequest chatClientRequest,
            StreamAdvisorChain streamAdvisorChain) {

        log.info("Streaming request started");
        return Flux.defer(() -> {

            long startTime = System.currentTimeMillis();

            log.info("AI stream started");

            AtomicReference<Usage> finalUsage =
                    new AtomicReference<>();

            return streamAdvisorChain.nextStream(chatClientRequest)

                    // Process every streaming response
                    .doOnNext(response -> {

                        ChatResponse chatResponse =
                                response.chatResponse();

                        if (chatResponse != null &&
                                chatResponse.getMetadata() != null) {

                            Usage usage = chatResponse
                                    .getMetadata()
                                    .getUsage();

                            if (usage != null) {
                                finalUsage.set(usage);
                            }
                        }
                    })

                    // Handle streaming errors
                    .doOnError(error ->
                            log.error("AI stream failed", error)
                    )

                    // Runs on completion, error or cancellation
                    .doFinally(signalType -> {

                        long duration =
                                System.currentTimeMillis() - startTime;

                        log.info(
                                "AI stream terminated: {}",
                                signalType
                        );

                        log.info(
                                "AI stream duration: {} ms",
                                duration
                        );

                        Usage usage = finalUsage.get();

                        logUsage(usage);
                    });
        });
    }

    // 5. Common token usage logging method
    private void logUsage(Usage usage) {

        if (usage == null) {
            log.info("Token usage information unavailable");
            return;
        }

        log.info(
                "PromptTokens={}, CompletionTokens={}, TotalTokens={}",
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens()
        );
    }
}
