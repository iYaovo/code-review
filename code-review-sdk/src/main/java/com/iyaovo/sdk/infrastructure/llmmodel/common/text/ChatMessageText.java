package com.iyaovo.sdk.infrastructure.llmmodel.common.text;

/**
 * 聊天文本接口
 *
 * @author iyaoV
 */
public interface ChatMessageText {

    /**
     * 标识聊天消息的类型
     * @return 聊天消息类枚举类
     */
    public ChatMessageTextType type();

    /**
     * 消息的文本对象
     * @return 消息的文本
     */
    public String text();
}
