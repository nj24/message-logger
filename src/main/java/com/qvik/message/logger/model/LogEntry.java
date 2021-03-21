package com.qvik.message.logger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class LogEntry {
    private final UUID id;
    private final String source;
    private final String message;
    private final ZonedDateTime createdDate;
    private final int maxAge;
}
