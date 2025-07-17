package com.iyaovo.sdk.infrastructure.llmmodel.zhipu;

import com.iyaovo.sdk.infrastructure.llmmodel.common.chat.ChatLanguageModel;
import com.iyaovo.sdk.infrastructure.llmmodel.common.output.Response;
import com.iyaovo.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import com.iyaovo.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.ChatMessageText;
import com.iyaovo.sdk.types.utils.Utils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class ZhipuAiChatModel implements ChatLanguageModel {

    private static final Logger log = LoggerFactory.getLogger(ZhipuAiChatModel.class);

    private final String model;
    private final ZhipuAiHttpClient client;

    @Builder
    public ZhipuAiChatModel(String baseUrl, String apiKey, String model) {
        this.model = Utils.getOrDefault(model, ZhipuChatCompletionModelEnum.GLM_4_FLASH.toString());
        this.client = ZhipuAiHttpClient.builder()
                .baseUrl(Utils.getOrDefault(baseUrl, "https://open.bigmodel.cn"))
                .apiKey(apiKey)
                .build();
    }

    @Override
    public Response<AIMessageText> generate(List<ChatMessageText> messages) {
        ChatCompletionRequest.ChatCompletionRequestBuilder builder = ChatCompletionRequest.builder()
                .model(model)
                .messages(ZhipuAIHelper.toMessages(messages));
        ChatCompletionRequest request = builder.build();
        ChatCompletionResponse response = client.chatCompletion(request);
        Response<AIMessageText> messageResponse = Response.from(ZhipuAIHelper.aiMessageFrom(response));
        return messageResponse;
    }
}