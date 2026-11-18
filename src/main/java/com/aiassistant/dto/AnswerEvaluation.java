package com.aiassistant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AnswerEvaluation(
        @Min(0)
        @Max(10)
        int score,
        boolean correct,
        @NotBlank
        String feedback,
        List<String> strengths,
        List<String> improvements,
        @NotBlank
        String modelAnswer
) {
}
