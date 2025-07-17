package com.iyaovo.sdk.test;

import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import com.iyaovo.sdk.infrastructure.message.MessageFactory;
import org.junit.Test;

public class FeishuTest {

    private static final String apiKey = System.getenv("CHATGLM_APIHOST");
    private static final String baseUrl = System.getenv("CHATGLM_APIKEYSECRET");

    @Test
    public void test1() throws Exception {
        IMessageStrategy feishu = MessageFactory.getMessageStrategy("feishu");
        System.out.println(feishu.name());
    }
}
