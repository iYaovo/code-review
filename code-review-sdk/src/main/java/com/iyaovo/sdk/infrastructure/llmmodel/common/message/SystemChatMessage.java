package com.iyaovo.sdk.infrastructure.llmmodel.common.message;

import lombok.Builder;
import lombok.Data;

/**
 * @author iyaovo
 */
@Data
@Builder
public final class SystemChatMessage implements ChatMessage {

    @Builder.Default
    private String role = Role.SYSTEM.toString().toLowerCase();
    private String content;
    private String name;

    public static SystemChatMessage from(String content) {
        return SystemChatMessage.builder()
                .content(content)
                .build();
    }
}