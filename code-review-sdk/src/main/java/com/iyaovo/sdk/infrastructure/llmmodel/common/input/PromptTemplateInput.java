package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

/**
 * 提示词模板输入内容
 *
 * @author iyaovo
 */
public interface PromptTemplateInput {

    /**
     * 获得提示词模板字符串
     * @return 模板字符串
     */
    String getTemplate();

    /**
     * 获得提示词模板名称
     * @return 模板的名称
     */
    default String getName() { return "template"; }
}