package com.iyaovo.sdk.infrastructure.message.impl;

import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import com.iyaovo.sdk.infrastructure.weixin.WeiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class WeixinMessageStrategy implements IMessageStrategy {

    private static final Logger log = LoggerFactory.getLogger(WeixinMessageStrategy.class);

    @Override
    public String name() {
        return "weixin";
    }

    @Override
    public void sendMessage(String logUrl, Map<String, Map<String, String>> data) {
        try {
            // 从环境变量获取微信配置，创建 WeiXin 实例
            WeiXin weiXin = new WeiXin(
                    System.getenv("WEIXIN_APPID"),
                    System.getenv("WEIXIN_SECRET"),
                    System.getenv("WEIXIN_TOUSER"),
                    System.getenv("WEIXIN_TEMPLATE_ID")
            );
            // 调用微信模板消息发送方法
            weiXin.sendTemplateMessage(logUrl, data);
        } catch (Exception e) {
            // 捕获异常并记录日志
            log.error("微信消息发送失败: {}", e.getMessage(), e);
        }
    }
}