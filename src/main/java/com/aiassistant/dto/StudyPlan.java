package com.aiassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record StudyPlan(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotEmpty
        List<StudyDay> days
) {
}
