package com.iyaovo.sdk.infrastructure.llmmodel.common.text;

import lombok.Builder;

@Builder
public class AIMessageText implements ChatMessageText {
    private final String text;

    public AIMessageText(String text) {
        this.text = text;
    }

    @Override
    public ChatMessageTextType type() {
        return ChatMessageTextType.AI;
    }

    @Override
    public String text() {
        return text;
    }

    public static AIMessageText from(String text) {
        return AIMessageText.builder()
                .text(text)
                .build();
    }
}
