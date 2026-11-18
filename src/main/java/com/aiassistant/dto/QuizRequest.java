package com.aiassistant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record QuizRequest(
        @NotBlank(message = "topic is required")
        String topic,
        @NotBlank(message = "Level is required")
        String level,
        @Min(1)
        @Max(20)
        int numberOfQuestions
) {
}
