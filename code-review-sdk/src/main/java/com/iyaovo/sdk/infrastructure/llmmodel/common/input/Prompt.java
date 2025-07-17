package com.iyaovo.sdk.infrastructure.llmmodel.common.input;

/**
 * @author Iyaov
 */
public class Prompt {

    private final String text;

    public Prompt(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    public static Prompt from(String text) {
        return new Prompt(text);
    }
}