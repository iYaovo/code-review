package com.iyaovo.sdk.infrastructure.message.impl;

import com.iyaovo.sdk.infrastructure.feishu.Feishu;
import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Slf4j
public class FeishuMessageStrategy implements IMessageStrategy {

    private static final Logger log = LoggerFactory.getLogger(FeishuMessageStrategy.class);

    @Override
    public String name() {
        return "feishu";
    }

    @Override
    public void sendMessage(String logUrl, Map<String, Map<String, String>> data) {
        try {
            System.out.println("url" + System.getenv("FEISHU_URI"));
            String botWebhook = System.getenv("FEISHU_URI");
            Feishu feishu = new Feishu(botWebhook);
            feishu.sendMessage(logUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}