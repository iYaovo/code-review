package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

/**
 * @author Iyaov
 */
public class DefaultPromptTemplateFactory implements PromptTemplateFactory {

    @Override
    public TemplateRender create(PromptTemplateInput input) {
        return new DefaultTemplateRender(input.getTemplate());
    }
}