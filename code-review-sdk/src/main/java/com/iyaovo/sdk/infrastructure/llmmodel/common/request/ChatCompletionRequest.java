package com.iyaovo.sdk.infrastructure.llmmodel.common.request;

import com.iyaovo.sdk.infrastructure.llmmodel.common.message.ChatMessage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author iyaovo
 */
@Data
@Builder
public class ChatCompletionRequest {

    private String model;
    private List<ChatMessage> messages;
    private String requestId;
    private Integer maxTokens;
}