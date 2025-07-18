package com.iyaovo.sdk.infrastructure.git.dto;

import lombok.Data;

/**
 * @author Iyaov
 */
@Data
public class CommitCommentRequestDTO {

    /**
     * 评论内容
     */
    private String body;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 行号位置
     */
    private int position;
}
