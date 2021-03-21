package com.qvik.message.logger.task;

import com.qvik.message.logger.repository.LogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class PurgeExpiredMessagesTask {
    Logger logger = LoggerFactory.getLogger(PurgeExpiredMessagesTask.class);

    private LogEntryRepository repository;

    @Value("${log-messages.purge.taskFixedDelay}")
    private long fixedDelay;

    @Autowired
    public PurgeExpiredMessagesTask(LogEntryRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelayString = "${log-messages.purge.taskFixedDelay}")
    public void purgeExpiredMessages() {
        logger.info("Running Scheduler with fixed delay {} at {}",fixedDelay , ZonedDateTime.now());
        repository.getLogs()
                .stream()
                .filter(logEntry -> repository.hasReachedMaxAge(logEntry))
                .forEach(logEntry -> repository.removeLogEntry(logEntry.getId()));
    }
}
