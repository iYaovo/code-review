package com.iyaovo.sdk.domain.service.impl;


import com.iyaovo.sdk.domain.service.AbstractOpenAiCodeReviewService;
import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;
import com.iyaovo.sdk.infrastructure.git.GitCommand;
import com.iyaovo.sdk.infrastructure.llmmodel.common.output.Response;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuAiChatModel;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuChatCompletionModelEnum;
import com.iyaovo.sdk.infrastructure.openai.IOpenAI;
import com.iyaovo.sdk.infrastructure.openai.impl.ChatGLM;
import com.iyaovo.sdk.infrastructure.weixin.WeiXin;
import com.iyaovo.sdk.infrastructure.weixin.dto.TemplateMessageDTO;

import java.util.HashMap;
import java.util.Map;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {

    private BaseGitOperation gitOperation;

    public OpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, WeiXin weiXin) {
        super(gitCommand, openAI, weiXin);
    }

    public OpenAiCodeReviewService(BaseGitOperation baseGitOperation,GitCommand gitCommand, IOpenAI openAI, WeiXin weiXin) {
        super(gitCommand, openAI, weiXin);
        this.gitOperation = gitOperation;
    }

    @Override
    protected String getDiffCode() throws Exception {
        return this.gitOperation.diff();
    }

    @Override
    protected String codeReview(String diffCode) throws Exception {
        // 系统提示词
        SystemMessageText systemMessageText = new SystemMessageText(
                "你是一个10年经验的Java代码审查专家,请帮助用户进行代码审查");
        // 用户提示词
        UserMessageText userMessageText = new UserMessageText("你是一个高级编程架构师, 精通各类场景方案、架构设计和编程语言请");
        // 智谱大模型
        ChatGLM glm = (ChatGLM) openAI;
        ZhipuAiChatModel chatModel = ZhipuAiChatModel.builder()
                .apiKey(glm.getApiKeySecret())
                .baseUrl(glm.getApiHost())
                .model(ZhipuChatCompletionModelEnum.GLM_4_FLASH.toString())
                .build();
        // 获得响应
        Response<AIMessageText> response = chatModel.generate(systemMessageText, userMessageText);
        System.out.println(response.content().text());
        return response.content().text();
    }

    @Override
    protected String recordCodeReview(String recommend) throws Exception {
        return gitCommand.commitAndPush(recommend);
    }

    @Override
    protected void pushMessage(String logUrl) throws Exception {
        Map<String, Map<String, String>> data = new HashMap<>();
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.REPO_NAME, gitCommand.getProject());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.BRANCH_NAME, gitCommand.getBranch());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_AUTHOR, gitCommand.getAuthor());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_MESSAGE, gitCommand.getMessage());
        weiXin.sendTemplateMessage(logUrl, data);
    }

}
