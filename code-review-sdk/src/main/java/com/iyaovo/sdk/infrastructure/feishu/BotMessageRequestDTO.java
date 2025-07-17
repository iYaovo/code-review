package com.iyaovo.sdk.infrastructure.feishu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Iyaov
 */
@Data
public class BotMessageRequestDTO {

    /**
     * 飞书的消息类型
     */
    @JSONField(name = "msg_type")
    private String msgType = "text";

    /**
     * 飞书的内容
     */
    @JSONField(name = "content")
    private BotMessageContent content;

    @Data
    public static class BotMessageContent {
        /**
         * 消息内容
         */
        private String text;
    }
}