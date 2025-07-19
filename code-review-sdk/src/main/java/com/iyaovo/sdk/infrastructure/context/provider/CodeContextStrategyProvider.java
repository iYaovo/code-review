package com.iyaovo.sdk.infrastructure.context.provider;

import com.iyaovo.sdk.infrastructure.context.enums.CodeContextStrategyProviderEnum;
import com.iyaovo.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import com.iyaovo.sdk.infrastructure.context.model.ProviderSwitchConfig;

/**
 * 代码上下文策略的提供者
 *
 * @author iyaovo
 */
public interface CodeContextStrategyProvider {

    /**
     * 类型
     * @return 标识类型
     */
    public CodeContextStrategyProviderEnum getType();

    /**
     * 判断当前是否支持
     * @return true代表支持
     */
    public boolean support(ProviderSwitchConfig statusData);

    /**
     * 执行上下文的构建
     * @return 上下文字符串
     */
    public String executeProviderBuild(ExecuteProviderParamContext context);
}