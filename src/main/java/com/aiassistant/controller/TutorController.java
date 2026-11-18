package com.aiassistant.controller;

import com.aiassistant.dto.*;
import com.aiassistant.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/tutor")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    //Streaming Response
    @PostMapping(value = "/ask/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAskAi(@RequestBody @Valid AiRequest aiRequest) {
        return tutorService.streamFromAi(aiRequest);
    }

    @PostMapping("/ask")
    public ResponseEntity<AiResponse> askFromAi(@RequestBody @Valid AiRequest aiRequest) {
        return ResponseEntity.ok(tutorService.askFromAi(aiRequest));
    }

    @PostMapping("/study-plan")
    public ResponseEntity<StudyPlan> studyPlan(@RequestBody @Valid StudyPlanRequest request) {
       StudyPlan studyPlan =  tutorService.prepareStudyPlan(request);
       return ResponseEntity.ok(studyPlan);
    }

    @PostMapping("/quiz")
    public ResponseEntity<Quiz> generateQuiz(@Valid @RequestBody QuizRequest request){
        return ResponseEntity.ok(tutorService.generateQuiz(request));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<AnswerEvaluation> evaluateAnswer(@Valid @RequestBody AnswerValidRequest request){
       return ResponseEntity.ok(tutorService.evaluateAnswer(request));
    }
}
