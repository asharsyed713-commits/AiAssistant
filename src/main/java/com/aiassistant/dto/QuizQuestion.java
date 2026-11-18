package com.aiassistant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuizQuestion(
        @Min(1)
        int questionNumber,

        @NotBlank
        String questionText,

        @NotEmpty
        @Size(min = 4, max = 4)
        List<String> options,

        @NotBlank
        String correctAnswer,

        @NotBlank
        String explanation,

        @Min(1)
        int marks
) {
}
