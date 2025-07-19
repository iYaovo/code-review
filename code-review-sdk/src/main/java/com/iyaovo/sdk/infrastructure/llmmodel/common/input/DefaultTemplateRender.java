package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认提示词模板处理代码
 *
 * @author iyaovo
 */
public class DefaultTemplateRender implements TemplateRender {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");
    private final String template;
    private final Set<String> allVariables;

    public DefaultTemplateRender(String template) {
        if (template == null || template.trim().length() == 0) {
            throw new RuntimeException("提示词模板不能为空");
        }
        this.template = template;
        this.allVariables = extractVariables(template);
    }

    /**
     * 这个方法负责从模板字符串中提取所有变量名。使用正则表达式 {{(.+?)}} 匹配出模板中的变量占位符，
     * 并将每个变量名放入一个 Set<String> 集合里，确保变量名是唯一的。
     * 这种处理方式让你可以轻松获取模板中所有需要替换的变量。
     * @param template
     * @return
     */
    private static Set<String> extractVariables(String template) {
        Set<String> variables = new HashSet<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }
        return variables;
    }

    /**
     * 当你想渲染模板时，可以调用这个方法，并传入一个包含变量名和对应值的 Map<String, Object>。方法的工作流程如下：
     * 调用 ensureAllVariablesProvided() 校验传入的变量是否覆盖了模板中所有需要替换的变量。如果有未提供的变量，程序会抛出异常，提醒用户补充。
     * 逐个替换模板中的变量占位符为实际值，并返回替换后的最终字符串。
     * @param variables the variables to use.
     * @return
     */
    public String render(Map<String, Object> variables) {
        ensureAllVariablesProvided(variables);
        String result = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            result = replaceAll(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void ensureAllVariablesProvided(Map<String, Object> providedVariables) {
        for (String variable : allVariables) {
            if (!providedVariables.containsKey(variable)) {
                throw new RuntimeException(String.format("Value for the variable '%s' is missing", variable));
            }
        }
    }

    private static String replaceAll(String template, String variable, Object value) {
        if (value == null || value.toString() == null) {
            throw new RuntimeException(String.format("Value for the variable '%s' is null", variable));
        }
        return template.replace(inDoubleCurlyBrackets(variable), value.toString());
    }

    private static String inDoubleCurlyBrackets(String variable) {
        return "{{" + variable + "}}";
    }
}