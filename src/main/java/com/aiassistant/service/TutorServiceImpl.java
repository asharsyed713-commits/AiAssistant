package com.aiassistant.service;

import com.aiassistant.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    @Value("classpath:/prompts/java-teacher-prompt.st")
    private Resource queryResource;

    @Value("classpath:/prompts/study-prompt.st")
    private Resource studyPlanPrompt;

    @Value("classpath:/prompts/quiz-prompt.st")
    private Resource quizPrompt;

    @Value("classpath:/prompts/answer-evaluation-prompt.st")
    private Resource answerEvaluationPrompt;
   
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

    public Quiz generateQuiz(QuizRequest request){
        return chatClient.prompt()
                .user(user->
                        user.text(quizPrompt)
                                .param("topic",request.topic())
                                .param("level",request.level())
                                .param("numberOfQuestions",request.numberOfQuestions()))
                .call()
                .entity(Quiz.class);
    }

    public AnswerEvaluation evaluateAnswer(AnswerValidRequest request){
        return chatClient.prompt()
                .options(
                        ChatOptions.builder()
                                .temperature(0.0)
                )
                .user(user-> user
                        .text(answerEvaluationPrompt)
                        .param("question",request.question())
                                .param("studentAnswer",request.studentAnswer())
                        .param("level",request.level())
                        )
                .call()
                .entity(AnswerEvaluation.class);
    }

    //Streaming Response
    @Override
    public Flux<String> streamFromAi(AiRequest aiRequest) {
        Flux<String> content = chatClient.prompt()
                .options(
                        ChatOptions.builder()
                                .temperature(0.3)
                                .maxTokens(1000)
                )
                .user(user ->
                        user.text(queryResource)
                                .param("topic", aiRequest.topic())
                                .param("level", aiRequest.level())
                                .param("language", aiRequest.language()))
                .stream()
                .content();
        return content;
    }
}
