package com.qvik.message.logger.service;

import com.qvik.message.logger.model.LogEntry;
import com.qvik.message.logger.model.LogStats;
import com.qvik.message.logger.model.MaxAgeRange;
import com.qvik.message.logger.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringStatisticsService {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public MonitoringStatisticsService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public LogStats getMonitoringStatistics() {
        final List<LogEntry> logs = logEntryRepository.getLogs();
        return LogStats.builder()
                .totalNumberOfLogs(logs.size())
                .maxAgeRange(MaxAgeRange.builder()
                        .endRange(logs.stream()
                                .mapToInt(LogEntry::getMaxAge)
                                .max()
                                .orElse(0))
                        .startRange(logs.stream()
                                .mapToInt(LogEntry::getMaxAge)
                                .min()
                                .orElse(0))
                        .averageMaxAge(logs.stream()
                                .mapToInt(LogEntry::getMaxAge)
                                .average()
                                .orElse(0))
                        .build())
                .build();
    }
}
