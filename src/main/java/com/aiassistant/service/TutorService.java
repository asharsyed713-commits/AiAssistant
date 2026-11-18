package com.aiassistant.service;

import com.aiassistant.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface TutorService {

    public AiResponse askFromAi(AiRequest aiRequest);

    StudyPlan prepareStudyPlan(StudyPlanRequest request);

    Quiz generateQuiz(QuizRequest request);

    AnswerEvaluation evaluateAnswer(AnswerValidRequest request);

    Flux<ServerSentEvent<AiStreamEvent>> streamFromAi(AiRequest aiRequest);
}
