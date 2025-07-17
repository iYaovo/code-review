package com.iyaovo.sdk.infrastructure.llmmodel.common.output;

/**
 * @author iyaovo
 */
public class Response<T> {

    private final T content;

    public Response(T content) {
        this.content = content;
    }

    public T content() {
        return content;
    }

    public static <T> Response<T> from(T content) {
        return new Response<>(content);
    }
}