package com.qvik.message.logger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LogStats {
    private final Integer totalNumberOfLogs;
    public final MaxAgeRange maxAgeRange;
}
