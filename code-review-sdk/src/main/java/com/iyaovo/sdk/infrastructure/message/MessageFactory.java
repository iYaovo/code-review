package com.iyaovo.sdk.infrastructure.message;

import com.iyaovo.sdk.infrastructure.message.impl.FeishuMessageStrategy;
import com.iyaovo.sdk.infrastructure.message.impl.WeixinMessageStrategy;

import java.util.HashMap;
import java.util.Map;

public class MessageFactory {

    private static Map<String, IMessageStrategy> registry = new HashMap<>();

    static {
        // 初始化2个策略实现
        registry.put("weixin", new WeixinMessageStrategy());
        registry.put("feishu", new FeishuMessageStrategy());
    }

    public static IMessageStrategy getMessageStrategy(String name) {
        return registry.get(name);
    }
}