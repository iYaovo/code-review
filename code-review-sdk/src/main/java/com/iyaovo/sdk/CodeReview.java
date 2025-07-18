package com.iyaovo.sdk;


import com.iyaovo.sdk.domain.service.impl.OpenAiCodeReviewService;
import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;
import com.iyaovo.sdk.infrastructure.git.GitCommand;
import com.iyaovo.sdk.infrastructure.git.GitRestAPIOperation;
import com.iyaovo.sdk.infrastructure.message.IMessageStrategy;
import com.iyaovo.sdk.infrastructure.message.MessageFactory;
import com.iyaovo.sdk.infrastructure.openai.IOpenAI;
import com.iyaovo.sdk.infrastructure.openai.impl.ChatGLM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeReview {

    private static final Logger logger = LoggerFactory.getLogger(CodeReview.class);

    // 配置配置
    private String weixin_appid = "wxa793ac3357fea6a9";
    private String weixin_secret = "d21640b1f83d1ba763f48d90721530b8";
    private String weixin_touser = "omhOgvi5o7ZjyblUWS6R845MF7k0";
    private String weixin_template_id = "HoxEAAVAfO-A24Ds5TgOQ8h7Lub4X5OeBYad6zzF-EU";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private String chatglm_apiKeySecret = "";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );


        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        BaseGitOperation baseGitOperation = new GitRestAPIOperation(getEnv("GIT_CHECK_COMMIT_URL"),getEnv("GITHUB_TOKEN"));

        System.out.println(baseGitOperation.toString());
        IMessageStrategy messageStrategy = MessageFactory.getMessageStrategy(getEnv("NOTIFY_TYPE"));

        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(baseGitOperation,gitCommand, openAI, messageStrategy);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }
}
