package com.aiassistant.dto;

import jakarta.validation.constraints.NotBlank;

public record QuizOption(
        @NotBlank
        String id,
        @NotBlank
        String text
) {
}
