package com.iyaovo.sdk.infrastructure.git;

import com.alibaba.fastjson2.JSON;
import com.iyaovo.sdk.infrastructure.git.dto.SingleCommitResponseDTO;
import com.iyaovo.sdk.types.utils.DefaultHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GitRestAPIOperation implements BaseGitOperation{

    private static final String AUTHORIZATION = "Authorization";
    private static final String GITHUB_API_VERSION = "X-GitHub-Api-Version";
    private static final String ACCEPT = "Accept";

    private final Logger logger = LoggerFactory.getLogger(GitRestAPIOperation.class);

    private final String githubRepoUrl;

    private final String githubToken;

    public GitRestAPIOperation(String githubRepoUrl, String githubToken) {
        this.githubRepoUrl = githubRepoUrl;
        this.githubToken = githubToken;
    }

    @Override
    public String diff() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put(GitRestAPIOperation.AUTHORIZATION,"Bearer " + githubToken);
        params.put(GitRestAPIOperation.ACCEPT,"application/vnd.github+json");
        params.put(GitRestAPIOperation.GITHUB_API_VERSION,"2022-11-28");

        String result = DefaultHttpUtil.executeGetRequest(githubRepoUrl, params);
        SingleCommitResponseDTO singleCommitResponseDTO = JSON.parseObject(result, SingleCommitResponseDTO.class);
        SingleCommitResponseDTO.CommitFile[] files = singleCommitResponseDTO.getFiles();
        StringBuilder sb = new StringBuilder();
        for(SingleCommitResponseDTO.CommitFile file : files){
            sb.append("待评审文件名称：").append(file.getFileName());
            sb.append("该文件变更代码：").append(file.getPatch());
        }
        return sb.toString();
    }
}
