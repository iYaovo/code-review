package com.iyaovo.sdk.infrastructure.git;

/**
 * @author Iyaov
 */
public interface BaseGitOperation {

    /**
     * 获取代码变更内容
     * @return 变更内容
     */
    public String diff() throws Exception;
}
