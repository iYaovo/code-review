package com.iyaovo.sdk.domain.service;

import com.iyaovo.sdk.domain.model.ExecuteCodeReviewRequestContext;
import com.iyaovo.sdk.infrastructure.git.GitCommand;
import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import com.iyaovo.sdk.infrastructure.openai.IOpenAI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author Iyaov
 */
public abstract class AbstractOpenAiCodeReviewService implements IOpenAiCodeReviewService {

    private final Logger logger = LoggerFactory.getLogger(AbstractOpenAiCodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final IOpenAI openAI;
    protected final IMessageStrategy messageStrategy;

    public AbstractOpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, IMessageStrategy messageStrategy) {
        System.out.println("这里应该有" + messageStrategy);
        this.gitCommand = gitCommand;
        this.openAI = openAI;
        this.messageStrategy = messageStrategy;
    }

    @Override
    public void exec() {
        try {
            //封装执行请求上下文进行业务数据处理
            ExecuteCodeReviewRequestContext context = new ExecuteCodeReviewRequestContext();

            // 1. 获取提交代码
            getDiffCode(context);
            // 2. 开始评审代码
            String recommend = codeReview(context);
            // 3. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            // 4. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
        } catch (Exception e) {
            logger.error("openai-code-review error", e);
        }

    }

    protected abstract void getDiffCode(ExecuteCodeReviewRequestContext context) throws IOException, InterruptedException, Exception;

    protected abstract String codeReview(ExecuteCodeReviewRequestContext context) throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl) throws Exception;

}
