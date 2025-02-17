package com.exercise.client_service.service.utils;

import java.util.function.Consumer;

public class UpdateUtil {
    public static  <T> void updateIfNotNull(T sourceValue, Consumer<T> setter) {
        if (sourceValue != null) {
            setter.accept(sourceValue);
        }
    }
}

