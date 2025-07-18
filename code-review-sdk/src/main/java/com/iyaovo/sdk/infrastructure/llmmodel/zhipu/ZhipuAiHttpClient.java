package com.iyaovo.sdk.infrastructure.llmmodel.zhipu;

import com.alibaba.fastjson2.JSON;
import com.iyaovo.sdk.infrastructure.llmmodel.common.request.ChatCompletionRequest;
import com.iyaovo.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import com.iyaovo.sdk.types.utils.DefaultHttpUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Builder
public class ZhipuAiHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ZhipuAiHttpClient.class);

    private String baseUrl;
    private String apiKey;

    public ChatCompletionResponse chatCompletion(ChatCompletionRequest request) {
        try {
            // 发送网络请求解析响应
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + apiKey);
            headers.put("Content-Type", "application/json");
            String url = this.baseUrl + "/api/paas/v4/chat/completions";
            System.out.println(JSON.toJSONString(request));
            String response = DefaultHttpUtil.executePostRequest(url, headers, request);
            ChatCompletionResponse chatCompletionResponse = JSON.parseObject(response, ChatCompletionResponse.class);
            return chatCompletionResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}