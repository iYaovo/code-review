package com.iyaovo.sdk.infrastructure.git.write.impl;

import com.iyaovo.sdk.infrastructure.git.BaseGitOperation;
import com.iyaovo.sdk.infrastructure.git.GitCommand;
import com.iyaovo.sdk.infrastructure.git.write.IWriteHandlerStrategy;

/**
 * @author Iyaov
 */
public class RemoteRepositoryWriteHandlerStrategy implements IWriteHandlerStrategy {

    private BaseGitOperation baseGitOperation;

    @Override
    public String typeName() {
        return "remote";
    }

    @Override
    public void initData(BaseGitOperation gitOperation) {
        this.baseGitOperation = gitOperation;
    }

    @Override
    public String execute(String codeResult) throws Exception {
        if (!(baseGitOperation instanceof GitCommand)) {
            throw new RuntimeException("Git操作对象不支持");
        }
        GitCommand gitCommand = (GitCommand) baseGitOperation;
        return gitCommand.writeResult(codeResult);
    }
}