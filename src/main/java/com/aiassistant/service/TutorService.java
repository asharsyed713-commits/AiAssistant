package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;
import com.aiassistant.dto.StudyPlan;
import com.aiassistant.dto.StudyPlanRequest;
import jakarta.validation.Valid;

public interface TutorService {

    public AiResponse askFromAi(AiRequest aiRequest);

    StudyPlan prepareStudyPlan(StudyPlanRequest request);
}
