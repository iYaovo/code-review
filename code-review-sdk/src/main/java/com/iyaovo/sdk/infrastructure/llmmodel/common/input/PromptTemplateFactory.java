package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

/**
 * 提示词模板工厂接口
 *
 * @author iyaovo
 */
public interface PromptTemplateFactory {

    /**
     * 创建一个提示词模板
     * @param input 输入参数
     * @return 提示词模板.
     */
    TemplateRender create(PromptTemplateInput input);
}