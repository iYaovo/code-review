package com.iyaovo.sdk.infrastructure.git;

import com.iyaovo.sdk.domain.model.CodeReviewFile;

import java.util.List;

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
     * 定义方法，获取待评审的文件列表
     * @return 评审文件列表
     * @throws Exception
     */
    public List<CodeReviewFile> diffFileList() throws Exception;

    /**
     * 写结果处理方法
     * @param result 评审结果
     * @return 跳转地址
     * @throws Exception
     */
    public String writeResult(String result) throws Exception;
}
