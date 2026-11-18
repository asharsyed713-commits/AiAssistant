package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import com.aiassistant.dto.StudyPlan;
import com.aiassistant.dto.StudyPlanRequest;
import com.aiassistant.dto.Quiz;
import com.aiassistant.dto.QuizRequest;
import com.aiassistant.dto.AnswerEvaluation;
import com.aiassistant.dto.AnswerValidRequest;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

public interface TutorService {

    public AiResponse askFromAi(AiRequest aiRequest);

    StudyPlan prepareStudyPlan(StudyPlanRequest request);

    Quiz generateQuiz(QuizRequest request);

    AnswerEvaluation evaluateAnswer(AnswerValidRequest request);

    Flux<String> streamFromAi(@Valid AiRequest aiRequest);
}
