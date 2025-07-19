package com.iyaovo.sdk.infrastructure.context.provider.impl;

import com.iyaovo.sdk.infrastructure.context.enums.CodeContextStrategyProviderEnum;
import com.iyaovo.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import com.iyaovo.sdk.infrastructure.context.model.ProviderSwitchConfig;
import com.iyaovo.sdk.infrastructure.context.provider.CodeContextStrategyProvider;

/**
 * 文件内容上下文的类
 *
 * @author iyaovo
 */
public class FileContentContextStrategyProvider implements CodeContextStrategyProvider {

    @Override
    public CodeContextStrategyProviderEnum getType() {
        return CodeContextStrategyProviderEnum.FILE_CONTENT;
    }

    @Override
    public boolean support(ProviderSwitchConfig statusData) {
        return Boolean.TRUE.equals(statusData.get(getType().getKey()));
    }

    @Override
    public String executeProviderBuild(ExecuteProviderParamContext context) {
        // 取出来文件内容
        Object fileData = context.get("fileData");
        if(fileData != null && fileData.toString() != null && fileData.toString().length() > 0){
            StringBuilder sb = new StringBuilder();
            sb.append("<评审文件完整内容上下文>");
            sb.append("以下为当前审查文件的完整文件内容，评审时可以作为参考:");
            sb.append(fileData.toString());
            sb.append("</评审文件完整内容上下文>");
            return sb.toString();
        }
        return "";
    }
}