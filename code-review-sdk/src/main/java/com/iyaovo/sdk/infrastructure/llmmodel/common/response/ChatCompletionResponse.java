package com.iyaovo.sdk.infrastructure.llmmodel.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ChatCompletionResponse {

    private String id;
    private Integer created;
    private String model;
    private List<ChatCompletionChoice> choices;
}