package com.aiassistant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record StudyDay(
        @Min(1)
        int day,
        @NotBlank
        String title,
        @NotEmpty
        List<String> topics,
        @NotEmpty
        List<String> tasks
){
}
