package com.iyaovo.sdk.domain.model;


import lombok.Data;

/**
 * 代码评审文件对象
 *
 * @author iyaovo
 */
@Data
public class CodeReviewFile {
    private String fileName;
    private String diff;
    private String fileContent;
}