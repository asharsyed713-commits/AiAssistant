package com.aiassistant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record StudyPlanRequest(
        @NotBlank
        String topic,
        @NotBlank
        String level,
        @Min(1)
        @Max(30)
        int days
) {
}
