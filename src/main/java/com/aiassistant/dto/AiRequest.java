package com.aiassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AiRequest(
        @NotBlank(message = "question is required")
        @Size(min = 1,message = "Minimum of 5 characters are required in topic")
        String topic,
        @NotBlank(message = "Level is required")
        String level,
        @NotBlank(message = "Language is required")
        String language
) {
}
