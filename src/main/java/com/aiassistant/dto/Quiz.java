package com.aiassistant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record Quiz(
        @NotBlank
        String title,

        @NotBlank
        String topic,

        @NotBlank
        String shortDescription,

        @NotBlank
        String longDescription,

        @Min(1)
        int totalMarks,

        @Min(0)
        int passingMarks,

        @Min(1)
        int durationMinutes,

        @NotBlank
        String difficulty,

        @NotBlank
        String category,

        @NotEmpty
        List<String> instructions,

        @NotEmpty
        List<QuizQuestion> questions
) {
}
