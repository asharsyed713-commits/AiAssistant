package com.aiassistant.dto;

public record AiStreamEvent(
        String type,
        String content
) {
}
