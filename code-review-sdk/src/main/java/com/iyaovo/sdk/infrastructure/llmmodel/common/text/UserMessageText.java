package com.iyaovo.sdk.infrastructure.llmmodel.common.text;

/**
 * 用户消息文本实现类
 *
 * @author iyaovo
 */
public class UserMessageText implements ChatMessageText {

    private final String text;

    public UserMessageText(String text) {
        this.text = text;
    }

    @Override
    public ChatMessageTextType type() {
        return ChatMessageTextType.USER;
    }

    @Override
    public String text() {
        return text;
    }
}

