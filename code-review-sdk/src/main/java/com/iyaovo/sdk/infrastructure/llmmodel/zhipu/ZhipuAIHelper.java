package com.iyaovo.sdk.infrastructure.llmmodel.zhipu;

import com.iyaovo.sdk.infrastructure.llmmodel.common.message.AssistantChatMessage;
import com.iyaovo.sdk.infrastructure.llmmodel.common.message.ChatMessage;
import com.iyaovo.sdk.infrastructure.llmmodel.common.message.SystemChatMessage;
import com.iyaovo.sdk.infrastructure.llmmodel.common.message.UserChatMessage;
import com.iyaovo.sdk.infrastructure.llmmodel.common.response.ChatCompletionResponse;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.ChatMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.UserMessageText;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author iyaovo
 * @description 用于将提示词文案对象，转换为聊天消息对象，这里将3种常见的类型进行了转换
 */
public class ZhipuAIHelper {

    public static List<ChatMessage> toMessages(List<ChatMessageText> messages) {
        return messages.stream()
                .map(ZhipuAIHelper::toMessages)
                .collect(Collectors.toList());
    }

    public static ChatMessage toMessages(ChatMessageText message) {
        if (message instanceof SystemMessageText) {
            return SystemChatMessage.from(((SystemMessageText) message).text());
        }

        if (message instanceof UserMessageText) {
            UserMessageText userMessage = (UserMessageText) message;
            return UserChatMessage.from(((UserMessageText) message).text());
        }

        if (message instanceof AIMessageText) {
            AIMessageText aiMessage = (AIMessageText) message;
            return AssistantChatMessage.from(((AIMessageText) message).text());
        }

        throw new IllegalArgumentException("Unknown message type: " + message.type());
    }

    static AIMessageText aiMessageFrom(ChatCompletionResponse response) {
        AssistantChatMessage message = response.getChoices().get(0).getMessage();
        return AIMessageText.from(message.getContent());
    }
}