package com.iyaovo.sdk.infrastructure.llmmodel.common.message;

import lombok.Builder;
import lombok.Data;

/**
 * @author iyaovo
 */
@Data
@Builder
public final class AssistantChatMessage implements ChatMessage {

    @Builder.Default
    private String role = Role.ASSISTANT.toString().toLowerCase();
    private String content;
    private String name;

    public static AssistantChatMessage from(String content) {
        return AssistantChatMessage.builder()
                .content(content)
                .build();
    }
}