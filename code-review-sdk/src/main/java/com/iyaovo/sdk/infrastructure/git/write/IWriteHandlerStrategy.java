package com.iyaovo.sdk.infrastructure.git.write;

import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;

/**
 * 评审结果写处理器策略接口
 *
 * @author iyaovo
 */
public interface IWriteHandlerStrategy {

    /**
     * 类型名称
     *
     * @return 类型名称字符串
     */
    String typeName();

    /**
     * 初始化数据
     *
     * @param gitOperation git 操作对象（需提前定义 BaseGitOperation 类）
     */
    void initData(BaseGitOperation gitOperation);

    /**
     * 执行写入处理策略
     *
     * @param codeResult 评审结果字符串
     * @return 跳转 URL 字符串
     * @throws Exception 执行过程中可能抛出的异常
     */
    String execute(String codeResult) throws Exception;
}