package com.iyaovo.sdk.test;

import com.iyaovo.sdk.infrastructure.llmmodel.common.input.DefaultTemplateRender;
import com.iyaovo.sdk.infrastructure.llmmodel.common.input.TemplateRender;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultTemplateRenderTest {

    @Test
    public void testSingleVarReplace() {
        String template = "你是一个{{language}}工程师";
        TemplateRender render = new DefaultTemplateRender(template);
        Map<String, Object> map = new HashMap<>();
        map.put("language", "java");
        String prompt = render.render(map);
        System.out.println(prompt);
        assertEquals("你是一个java工程师", prompt);
    }
}