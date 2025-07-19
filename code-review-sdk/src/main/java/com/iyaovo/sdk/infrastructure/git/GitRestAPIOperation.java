package com.iyaovo.sdk.infrastructure.git;

import com.alibaba.fastjson2.JSON;
import com.iyaovo.sdk.domain.model.CodeReviewFile;
import com.iyaovo.sdk.infrastructure.git.dto.CommitCommentRequestDTO;
import com.iyaovo.sdk.infrastructure.git.dto.SingleCommitResponseDTO;
import com.iyaovo.sdk.types.utils.DefaultHttpUtil;
import com.iyaovo.sdk.types.utils.DiffParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            sb.append("待评审文件名称：").append(file.getFilename());
            sb.append("该文件变更代码：").append(file.getPatch());
        }
        return sb.toString();
    }

    @Override
    public List<CodeReviewFile> diffFileList() throws Exception {
        SingleCommitResponseDTO responseDTO = getCommitResponse();
        SingleCommitResponseDTO.CommitFile[] files = responseDTO.getFiles();
        List<CodeReviewFile> list = new ArrayList<>();
        for(SingleCommitResponseDTO.CommitFile file : files) {
            CodeReviewFile codeReviewFile = new CodeReviewFile();
            codeReviewFile.setFileName(file.getFilename());
            codeReviewFile.setDiff(file.getPatch());
            //发送请求获得文件内容
            String result = DefaultHttpUtil.executeGetRequest(file.getRaw_url(), new HashMap<>());
            codeReviewFile.setFileContent(result);
            list.add(codeReviewFile);
        }
        return list;
    }

    @Override
    public String writeResult(String codeResult) throws Exception {
        SingleCommitResponseDTO responseDTO = getCommitResponse();
        SingleCommitResponseDTO.CommitFile[] files = responseDTO.getFiles();
        for (SingleCommitResponseDTO.CommitFile file : files) {
            String patch = file.getPatch();
            // GitHub 的 Commit Comment API 需要的是变更字符串中的索引
            int diffPositionIndex = DiffParseUtil.parseLastDiffPosition(patch);
            CommitCommentRequestDTO request = new CommitCommentRequestDTO();
            request.setBody(codeResult);
            request.setPosition(diffPositionIndex);
            request.setPath(file.getFilename());
            logger.info("写入注释请求参数:{}", JSON.toJSONString(request));
            writeCommentRequest(request);
            logger.info("写入评审到注释区域处理完成");
            // 由于之前的评审是一次性评审多个文件，所以这里我们让他暂时只执行一次。未来优化下
            break;
        }
        return responseDTO.getHtml_url();
    }

    /**
     * 写评论注释
     * @param request 请求信息
     * @return 跳转URL
     * @throws Exception
     */
    private String writeCommentRequest(CommitCommentRequestDTO request) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-GitHub-Api-Version", "2022-11-28");
        headers.put("Authorization", "Bearer " + githubToken);
        headers.put("Accept", "application/vnd.github+json");
        String responseText = DefaultHttpUtil.executePostRequest(this.githubRepoUrl + "/comments", headers, request);
        return responseText;
    }

    private SingleCommitResponseDTO getCommitResponse() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-GitHub-Api-Version", "2022-11-28");
        headers.put("Authorization", "Bearer " + githubToken);
        headers.put("Accept", "application/vnd.github+json");
        String result = DefaultHttpUtil.executeGetRequest(this.githubRepoUrl, headers);
        //TODO delete
        System.out.println("来这里看请求结果" + result);
        SingleCommitResponseDTO singleCommitResponseDTO = JSON.parseObject(result, SingleCommitResponseDTO.class);
        SingleCommitResponseDTO.CommitFile[] files = singleCommitResponseDTO.getFiles();
        //TODO delete
        System.out.println(files[0].getFilename());
//        for (SingleCommitResponseDTO.CommitFile file : files) {
//            String patch = file.getPatch();
//            int diffPositionIndex = DiffParseUtil.parseLastDiffPosition(patch);
//            CommitCommentRequestDTO request = new CommitCommentRequestDTO();
//            request.setBody(patch);
//            request.setPosition(diffPositionIndex);
//            request.setPath(file.getFileName());
//        }
        return singleCommitResponseDTO;
    }
}
