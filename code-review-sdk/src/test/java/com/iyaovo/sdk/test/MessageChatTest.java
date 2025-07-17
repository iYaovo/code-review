package com.iyaovo.sdk.test;

import com.iyaovo.sdk.infrastructure.llmmodel.common.output.Response;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuAiChatModel;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuChatCompletionModelEnum;
import org.junit.Test;

public class MessageChatTest {

    private static final String apiKey = System.getenv("CHATGLM_APIHOST");
    private static final String baseUrl = System.getenv("CHATGLM_APIKEYSECRET");

    @Test
    public void test1() {
        // 系统提示词
        SystemMessageText systemMessageText = new SystemMessageText(
                "你是一个10年经验的Java代码审查专家,请帮助用户进行代码审查");
        // 用户提示词
        UserMessageText userMessageText = new UserMessageText("这个是我的代码评审内容:System.out.println(\"hello\")");
        // 智谱大模型
        ZhipuAiChatModel chatModel = ZhipuAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .model(ZhipuChatCompletionModelEnum.GLM_4_FLASH.toString())
                .build();
        // 获得响应
        Response<AIMessageText> response = chatModel.generate(systemMessageText, userMessageText);
        System.out.println(response.content().text());
    }
}
