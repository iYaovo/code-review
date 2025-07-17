package com.iyaovo.sdk.infrastructure.feishu;

import com.iyaovo.sdk.types.utils.DefaultHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Feishu {

    private final Logger logger = LoggerFactory.getLogger(Feishu.class);
    private final String botWebhook;

    public Feishu(String botWebhook) {
        this.botWebhook = botWebhook;
    }

    public void sendMessage(String redirectUrl) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        BotMessageRequestDTO request = new BotMessageRequestDTO();
        BotMessageRequestDTO.BotMessageContent content = new BotMessageRequestDTO.BotMessageContent();

        StringBuilder sb = new StringBuilder();
        sb.append("您好，你的GitHub Action的AI代码评审已处理完成，请查看结果：");
        sb.append(redirectUrl);
        content.setText(sb.toString());
        request.setContent(content);

        String result = DefaultHttpUtil.executePostRequest(this.botWebhook, headers, request);
        logger.info("飞书消息发送结果:{}", result);
    }
}