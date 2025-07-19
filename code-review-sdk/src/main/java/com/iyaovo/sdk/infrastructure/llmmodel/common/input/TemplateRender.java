package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

import java.util.Map;

/**
 * 提示词模板渲染实现的接口
 *
 * @author iyaovo
 */
public interface TemplateRender {

    /**
     * Render the template.
     * @param variables the variables to use.
     * @return the rendered template.
     */
    String render(Map<String, Object> variables);
}