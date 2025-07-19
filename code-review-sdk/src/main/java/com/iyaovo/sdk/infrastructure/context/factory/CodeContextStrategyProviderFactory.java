package com.iyaovo.sdk.infrastructure.context.factory;

import com.iyaovo.sdk.infrastructure.context.enums.CodeContextStrategyProviderEnum;
import com.iyaovo.sdk.infrastructure.context.provider.CodeContextStrategyProvider;
import com.iyaovo.sdk.infrastructure.context.provider.impl.FileContentContextStrategyProvider;
import com.iyaovo.sdk.infrastructure.context.provider.impl.FileTypeContextStrategyProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码上下文提供者工厂类
 *
 * @author iyaovo
 */
public class CodeContextStrategyProviderFactory {

    public static Map<String, CodeContextStrategyProvider> REGISTER_MAP = new HashMap<>();

    static {
        //  这里手动注册，未来可以使用SPI或者IOC
        REGISTER_MAP.put(CodeContextStrategyProviderEnum.FILE_CONTENT.getKey(), new FileContentContextStrategyProvider());
        REGISTER_MAP.put(CodeContextStrategyProviderEnum.FILE_TYPE.getKey(), new FileTypeContextStrategyProvider());
//        REGISTER_MAP.put(CodeContextStrategyProviderEnum.COMMIT_MESSAGE.getKey(), new CommitMessageContextStrategyProvider());
    }

    public static CodeContextStrategyProvider getStrategyProvider(String strategy) {
        return REGISTER_MAP.get(strategy);
    }
}