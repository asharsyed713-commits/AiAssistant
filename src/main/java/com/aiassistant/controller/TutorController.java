package com.aiassistant.controller;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import com.aiassistant.dto.StudyPlan;
import com.aiassistant.dto.StudyPlanRequest;
import com.aiassistant.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tutor")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping("/ask")
    public ResponseEntity<AiResponse> askFromAi(@RequestBody @Valid AiRequest aiRequest) {
        return ResponseEntity.ok(tutorService.askFromAi(aiRequest));
    }

    @PostMapping("/study-plan")
    public ResponseEntity<StudyPlan> studyPlan(@RequestBody @Valid StudyPlanRequest request) {
       StudyPlan studyPlan =  tutorService.prepareStudyPlan(request);
       return ResponseEntity.ok(studyPlan);
    }
}
