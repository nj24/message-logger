package com.qvik.message.logger.repository;

import com.qvik.message.logger.model.LogEntry;
import com.qvik.message.logger.model.LogEntryRequest;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class LogEntryRepository {

    private final ConcurrentHashMap<UUID, LogEntry> logEntries;

    public LogEntryRepository() {
        logEntries = new ConcurrentHashMap<>();
    }

    public List<LogEntry> getLogs() {
        return logEntries.values()
                .stream()
                .filter(logEntry -> !hasReachedMaxAge(logEntry))
                .collect(Collectors.toList());
    }

    public Optional<LogEntry> getLog(UUID logId) {
        final LogEntry logEntry = logEntries.get(logId);
        return logEntry == null || hasReachedMaxAge(logEntry) ? Optional.empty() : Optional.of(logEntry);
    }

    public LogEntry addLog(LogEntryRequest request) {
        LogEntry logEntry = LogEntry.builder()
                .id(UUID.randomUUID())
                .createdDate(ZonedDateTime.now())
                .message(request.getMessage())
                .source(request.getSource())
                .maxAge(request.getMaxAge())
                .build();

        logEntries.put(logEntry.getId(), logEntry);
        return logEntry;
    }

    public void removeLogEntry(UUID id){
        logEntries.remove(id);
    }

    public boolean hasReachedMaxAge(LogEntry logEntry) {
        return logEntry != null && ZonedDateTime.now().isAfter(logEntry
                .getCreatedDate()
                .plusSeconds(logEntry.getMaxAge()));
    }
}
