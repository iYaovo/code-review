package com.iyaovo.sdk.infrastructure.context.enums;

/**
 * 代码上下文策略提供者枚举类
 * @author iyaovo
 */
public enum CodeContextStrategyProviderEnum {

    /**
     * 文件内容
     */
    FILE_CONTENT("filecontent", "文件内容"),

    /**
     * 提交信息
     */
    COMMIT_MESSAGE("commitmessage", "提交信息"),

    /**
     * 文件类型
     */
    FILE_TYPE("filetype", "文件类型");

    private final String key;
    private final String name;

    CodeContextStrategyProviderEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}