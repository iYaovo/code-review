package com.iyaovo.sdk.infrastructure.context.provider.impl;

import com.iyaovo.sdk.infrastructure.context.enums.CodeContextStrategyProviderEnum;
import com.iyaovo.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import com.iyaovo.sdk.infrastructure.context.model.ProviderSwitchConfig;
import com.iyaovo.sdk.infrastructure.context.provider.CodeContextStrategyProvider;

/**
 * 文件类型上下文的类
 *
 * @author iyaovo
 */
public class FileTypeContextStrategyProvider implements CodeContextStrategyProvider {

    @Override
    public CodeContextStrategyProviderEnum getType() {
        return CodeContextStrategyProviderEnum.FILE_TYPE;
    }

    @Override
    public boolean support(ProviderSwitchConfig statusData) {
        return Boolean.TRUE.equals(statusData.get(getType().getKey()));
    }

    @Override
    public String executeProviderBuild(ExecuteProviderParamContext context) {
        // 取出来文件内容
        Object fileName = context.get("fileName");
        if(fileName != null && fileName.toString() != null && fileName.toString().length() > 0){
            StringBuilder sb = new StringBuilder();
            sb.append("<特定文件类型说明>");
            sb.append("以下为当前审查文件的完整文件内容，评审时可以作为参考:");
            String fileStr = fileName.toString();
            if(fileStr.endsWith(".java")) {
                sb.append("如果当前评审的文件类型是Java格式文件，请必须在评审结果中返回当前代码中设计到的技术知识点有哪些。");
            }
            if(fileStr.endsWith(".xml")) {
                sb.append("如果当前评审的文件类型是XML格式，请在评审结果中返回相关SQL是否存在慢SQL");
            }
            sb.append("</特定文件类型说明>");
            return sb.toString();
        }
        return "";
    }
}