package com.iyaovo.sdk.types.utils;

import java.util.function.Supplier;

/**
 * @author iyaovo
 */
public class Utils {

    private Utils() {}

    public static <T> T getOrDefault(T value, T defaultValue) {
        return value != null? value : defaultValue;
    }

    public static <T> T getOrDefault(T value, Supplier<T> defaultValueSupplier) {
        return value != null? value : defaultValueSupplier.get();
    }
}