package com.iyaovo.sdk.infrastructure.llmmodel.common.chat;

import com.iyaovo.sdk.infrastructure.llmmodel.common.output.Response;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.ChatMessageText;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * 标准AI大模型的客户端使用的API接口，各个大模型实现此接口
 *
 * @author iyaovo
 */
public interface ChatLanguageModel {

    /**
     * 可变参数接收多个消息提示词，返回AI响应的结果
     * @param messages 提示词
     * @return AI的返回结果
     */
    default Response<AIMessageText> generate(ChatMessageText... messages) {
        return generate(asList(messages));
    }

    /**
     * 列表结构接收多个消息提示词，返回AI响应的结果
     * @param messages 提示词
     * @return AI的返回结果
     */
    Response<AIMessageText> generate(List<ChatMessageText> messages);
}