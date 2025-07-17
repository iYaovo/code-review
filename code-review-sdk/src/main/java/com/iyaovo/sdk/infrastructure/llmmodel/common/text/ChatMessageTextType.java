package com.iyaovo.sdk.infrastructure.llmmodel.common.text;

/**
 * 聊天消息提示词文本类型枚举类
 */
public enum ChatMessageTextType {

    /**
     * 系统消息
     */
    SYSTEM(SystemMessageText.class),

    /**
     * 用户消息
     */
    USER(UserMessageText.class),

    /**
     * AI反馈的结果消息
     */
    AI(AIMessageText.class);

    private final Class<? extends ChatMessageText> messageClass;

    ChatMessageTextType(Class<? extends ChatMessageText> messageClass) {
        this.messageClass = messageClass;
    }

    /**
     * Returns the class of the message type.
     *
     * @return the class of the message type.
     */
    public Class<? extends ChatMessageText> messageClass() {
        return messageClass;
    }
}
