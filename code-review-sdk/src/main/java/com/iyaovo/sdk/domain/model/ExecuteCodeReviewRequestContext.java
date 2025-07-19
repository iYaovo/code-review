package com.iyaovo.sdk.domain.model;


import lombok.Data;

import java.util.List;

/**
 * 执行CodeReview的上下文
 *
 * @author iyaovo
 */
@Data
public class ExecuteCodeReviewRequestContext {
    /**
     * 待评审的内容
     */
    private String diffCode;
    /**
     * 待评审的文件内容列表
     */
    private List<CodeReviewFile> fileList;
}