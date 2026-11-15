package com.aiassistant.service;

import com.aiassistant.dto.AiRequest;
import com.aiassistant.dto.AiResponse;

public interface TutorService {

    public AiResponse askFromAi(AiRequest aiRequest);
}
