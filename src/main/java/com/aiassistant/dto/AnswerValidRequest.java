package com.aiassistant.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerValidRequest(
        @NotBlank(message = "Question is required")
        String question,
        @NotBlank(message = "Answer is required")
        String studentAnswer,
        @NotBlank(message = "Level is required")
        String level
) {
}
