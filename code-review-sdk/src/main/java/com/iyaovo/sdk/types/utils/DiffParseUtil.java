package com.iyaovo.sdk.types.utils;

/**
 * @author Iyaov
 */
public class DiffParseUtil {

    public static int parseLastDiffPosition(String fileDiff) {
        if (fileDiff == null || fileDiff.trim().isEmpty()) {
            return -1;
        }
        String[] lines = fileDiff.split("\n");
        return lines.length - 1;
    }

    public static void main(String[] args) {
        String testDiff = "示例diff内容行1\n示例diff内容行2";
        System.out.println("解析得到的行索引: " + parseLastDiffPosition(testDiff));
    }
}