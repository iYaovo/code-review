package com.iyaovo.sdk.infrastructure.git.write;

import com.iyaovo.sdk.infrastructure.git.write.impl.CommitCommentWriteHandlerStrategy;
import com.iyaovo.sdk.infrastructure.git.write.impl.RemoteRepositoryWriteHandlerStrategy;

import java.util.HashMap;
import java.util.Map;

public class WriteHandlerStrategyFactory {

    private static Map<String, IWriteHandlerStrategy> registry = new HashMap<>();

    static {
        // 初始化2个策略实现
        registry.put("comment", new CommitCommentWriteHandlerStrategy());
        registry.put("remote", new RemoteRepositoryWriteHandlerStrategy());
    }

    public static IWriteHandlerStrategy getStrategy(String name) {
        return registry.get(name);
    }
}