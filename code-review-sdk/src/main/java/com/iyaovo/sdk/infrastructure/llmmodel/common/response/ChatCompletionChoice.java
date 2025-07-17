package com.iyaovo.sdk.infrastructure.llmmodel.common.response;

import com.iyaovo.sdk.infrastructure.llmmodel.common.message.AssistantChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ChatCompletionChoice {

    private Integer index;
    private AssistantChatMessage message;
    private String finishReason;
}