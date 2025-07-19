package com.iyaovo.sdk.domain.service.impl;


import com.iyaovo.sdk.domain.model.CodeReviewFile;
import com.iyaovo.sdk.domain.model.ExecuteCodeReviewRequestContext;
import com.iyaovo.sdk.domain.service.AbstractOpenAiCodeReviewService;
import com.iyaovo.sdk.infrastructure.context.enums.CodeContextStrategyProviderEnum;
import com.iyaovo.sdk.infrastructure.context.factory.CodeContextStrategyProviderFactory;
import com.iyaovo.sdk.infrastructure.context.model.ExecuteProviderParamContext;
import com.iyaovo.sdk.infrastructure.context.model.ProviderSwitchConfig;
import com.iyaovo.sdk.infrastructure.context.provider.CodeContextStrategyProvider;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    protected void getDiffCode(ExecuteCodeReviewRequestContext context) throws Exception {
        List<CodeReviewFile> fileList = this.gitOperation.diffFileList();
        context.setFileList(fileList);
        context.setDiffCode(this.gitOperation.diff());
    }

    @Override
    protected String codeReview(ExecuteCodeReviewRequestContext context) throws Exception {
        List<CodeReviewFile> fileList = context.getFileList();
        StringBuilder result = new StringBuilder();
        for(CodeReviewFile file : fileList) {

                // 系统提示词模板的定义
                PromptTemplate promptTemplate = PromptTemplate.from("你是一个10年经验的{{language}}高级代码审查专家,请帮助用户进行代码审查");
                Map<String, Object> params = new HashMap<>();
                params.put("language", "java");
                Prompt prompt = promptTemplate.apply(params);
                String promptText = prompt.text();

                //TODO 这里我们定义一个开关配置，组装哪些内容，未来也可以更改为从配置中读取
                //TODO 这里还少了个CommitMessageContextStrategyProvider，通过提交信息去作为上下文
                ProviderSwitchConfig switchConfig = new ProviderSwitchConfig();
                switchConfig.put(CodeContextStrategyProviderEnum.FILE_CONTENT.getKey(), true);
                switchConfig.put(CodeContextStrategyProviderEnum.FILE_TYPE.getKey(), true);
                // 定义参数
                ExecuteProviderParamContext param =  new ExecuteProviderParamContext();
                param.put("fileData", file.getFileContent());
                param.put("fileName", file.getFileName());

                Set<String> keySet = switchConfig.keySet();
                for(String contextKey : keySet) {
                    CodeContextStrategyProvider strategyProvider = CodeContextStrategyProviderFactory.getStrategyProvider(
                            contextKey);
                    if(strategyProvider != null) {
                        String data = strategyProvider.executeProviderBuild(param);
                        promptText = promptText + data;
                    }
                }

                // 组装系统消息
                SystemMessageText systemMessageText = new SystemMessageText(promptText);
                // 组装用户消息
                UserMessageText userMessageText = buildUserMessageText(file);
                // 调用HTTP AI大模型接口
                Response<AIMessageText> response = executeAiRequest(systemMessageText, userMessageText);
                //System.out.println(response.content().text());
                result.append("文件名称:").append(file.getFileName()).append(", 评审结果:").append(response.content().text()).append("\n");
            }
            return result.toString();
    }

    private UserMessageText buildUserMessageText(CodeReviewFile file) {
        // 用户提示词模板的定义
        String userTemplate = "请您根据git diff记录，对代码做出评审。变更代码如下:{{diffCode}}";
        PromptTemplate userPromptTemplate = PromptTemplate.from(userTemplate);
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("diffCode", file.getDiff());
        Prompt userPrompt = userPromptTemplate.apply(userParams);
        // 用户消息
        UserMessageText userMessageText = new UserMessageText(userPrompt.text());
        return userMessageText;
    }

    private Response<AIMessageText> executeAiRequest(SystemMessageText systemMessageText,
                                                     UserMessageText userMessageText) {
        // 智普大模型的配置
        ChatGLM glm = (ChatGLM) openAI;
        ZhipuAiChatModel chatModel = ZhipuAiChatModel.builder()
                .apiKey(glm.getApiKeySecret())
                .baseUrl(glm.getApiHost())
                .model(ZhipuChatCompletionModelEnum.GLM_4_FLASH.toString())
                .build();
        // 调用统一的大模型API设计接口获得响应
        Response<AIMessageText> response = chatModel.generate(systemMessageText, userMessageText);
        return response;
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
