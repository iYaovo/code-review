package com.iyaovo.sdk.infrastructure.openai;


import com.iyaovo.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import com.iyaovo.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;

}
