package com.iyaovo.sdk.infrastructure.git.write.impl;

import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;
import com.iyaovo.sdk.infrastructure.git.GitRestAPIOperation;
import com.iyaovo.sdk.infrastructure.git.write.IWriteHandlerStrategy;

public class CommitCommentWriteHandlerStrategy implements IWriteHandlerStrategy {

    private BaseGitOperation baseGitOperation;

    @Override
    public String typeName() {
        return "comment";
    }

    @Override
    public void initData(BaseGitOperation gitOperation) {
        this.baseGitOperation = gitOperation;
    }

    @Override
    public String execute(String codeResult) throws Exception {
        if (!(baseGitOperation instanceof GitRestAPIOperation)) {
            throw new RuntimeException("Git操作对象不支持");
        }
        GitRestAPIOperation restAPIOperation = (GitRestAPIOperation) baseGitOperation;
        return restAPIOperation.writeResult(codeResult);
    }
}