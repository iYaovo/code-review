package com.iyaovo.sdk.infrastructure.message;

import java.util.Map;

public interface IMessageStrategy {

    /**
     * 类型名称
     * @return 字符串名称
     */
    String name();

    /**
     * 发送消息接口方法
     * @param logUrl 跳转的URL
     * @param data 参数
     */
    void sendMessage(String logUrl, Map<String, Map<String, String>> data);
}