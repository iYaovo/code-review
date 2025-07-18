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

    /**
     * 写结果处理方法
     * @param result 评审结果
     * @return 跳转地址
     * @throws Exception
     */
    public String writeResult(String result) throws Exception;
}
