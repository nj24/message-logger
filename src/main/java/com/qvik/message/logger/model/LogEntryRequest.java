package com.qvik.message.logger.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Builder
@Getter
public class LogEntryRequest {
    @NonNull
    private final String message;
    @NonNull
    private final String source;
    @NonNull
    private final int maxAge;
}
