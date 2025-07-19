package com.iyaovo.sdk.domain.service.impl;


import com.iyaovo.sdk.domain.service.AbstractOpenAiCodeReviewService;
import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;
import com.iyaovo.sdk.infrastructure.git.GitCommand;
import com.iyaovo.sdk.infrastructure.git.write.IWriteHandlerStrategy;
import com.iyaovo.sdk.infrastructure.git.write.WriteHandlerStrategyFactory;
import com.iyaovo.sdk.infrastructure.llmmodel.common.input.Prompt;
import com.iyaovo.sdk.infrastructure.llmmodel.common.input.PromptTemplate;
import com.iyaovo.sdk.infrastructure.llmmodel.common.output.Response;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.AIMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.SystemMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.common.text.UserMessageText;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuAiChatModel;
import com.iyaovo.sdk.infrastructure.llmmodel.zhipu.ZhipuChatCompletionModelEnum;
import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import com.iyaovo.sdk.infrastructure.openai.IOpenAI;
import com.iyaovo.sdk.infrastructure.openai.impl.ChatGLM;
import com.iyaovo.sdk.infrastructure.weixin.dto.TemplateMessageDTO;

import java.util.HashMap;
import java.util.Map;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {

    private BaseGitOperation gitOperation;

    public OpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, IMessageStrategy messageStrategy) {
        super(gitCommand, openAI, messageStrategy);
    }

    public OpenAiCodeReviewService(BaseGitOperation baseGitOperation, GitCommand gitCommand, IOpenAI openAI, IMessageStrategy messageStrategy) {
        super(gitCommand, openAI, messageStrategy);
        this.gitOperation = baseGitOperation;
    }

    @Override
    protected String getDiffCode() throws Exception {
        return this.gitOperation.diff();
    }

    @Override
    protected String codeReview(String diffCode) throws Exception {
        // 系统提示词模板的定义
        PromptTemplate promptTemplate = PromptTemplate.from("你是一个10年经验的{{language}}高级代码审查专家，请帮助用户进行代码审查");
        Map<String, Object> params = new HashMap<>();
        params.put("language", "java");
        Prompt prompt = promptTemplate.apply(params);
        // 系统消息
        SystemMessageText systemMessageText = new SystemMessageText(prompt.text());

        // 用户提示词模板的定义
        String userTemplate = "请您根据git diff记录，对代码做出评审。变更代码如下:{{diffCode}}";
        PromptTemplate userPromptTemplate = PromptTemplate.from(userTemplate);
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("diffCode", diffCode);
        Prompt userPrompt = userPromptTemplate.apply(userParams);
        // 用户消息
        UserMessageText userMessageText = new UserMessageText(userPrompt.text());

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
        // TODO 先临时定一个变量，未来更改为外部传递
        String writeHandler = "comment";
        // 根据配置的情况进行策略的处理
        IWriteHandlerStrategy strategyHandler = WriteHandlerStrategyFactory.getStrategy(writeHandler);
        // 这里暂时我们写成git rest api 后续重构下Git的策略
        strategyHandler.initData(gitOperation);
        // 调用策略处理器处理下
        return strategyHandler.execute(recommend);
    }

    @Override
    protected void pushMessage(String logUrl) throws Exception {
        Map<String, Map<String, String>> data = new HashMap<>();
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.REPO_NAME, gitCommand.getProject());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.BRANCH_NAME, gitCommand.getBranch());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_AUTHOR, gitCommand.getAuthor());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_MESSAGE, gitCommand.getMessage());
        messageStrategy.sendMessage(logUrl, data);
    }

}
