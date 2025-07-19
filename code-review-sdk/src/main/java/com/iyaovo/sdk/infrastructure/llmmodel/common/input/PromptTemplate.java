package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

import java.util.Map;

public class PromptTemplate {

    private static final PromptTemplateFactory FACTORY = new DefaultPromptTemplateFactory();
    private final String templateString;
    private final TemplateRender templateRender;

    PromptTemplate(String template) {
        this.templateString = template;
        this.templateRender = FACTORY.create(new PromptTemplateInput() {
            @Override
            public String getTemplate() {
                return template;
            }
        });
    }

    public String template() {
        return templateString;
    }

    /**
     * 对外提供的方法，渲染变量为一个提示词对象
     * @param variables 变量
     * @return 提示词对象
     */
    public Prompt apply(Map<String, Object> variables) {
        return Prompt.from(templateRender.render(variables));
    }

    /**
     * 根据模板创建一个提示词模板
     * @param template 模板字符串
     * @return 模板对象
     */
    public static PromptTemplate from(String template) {
        return new PromptTemplate(template);
    }
}