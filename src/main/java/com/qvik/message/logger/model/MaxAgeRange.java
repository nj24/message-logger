package com.qvik.message.logger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MaxAgeRange {
    private final int startRange;
    private final int endRange;
    private final double averageMaxAge;
}
