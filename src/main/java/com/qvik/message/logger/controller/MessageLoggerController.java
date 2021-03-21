package com.qvik.message.logger.controller;


import com.qvik.message.logger.exception.MessageLoggerException;
import com.qvik.message.logger.model.LogEntry;
import com.qvik.message.logger.model.LogEntryRequest;
import com.qvik.message.logger.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/logs")
public class MessageLoggerController {

    @Autowired
    private LogEntryRepository logEntryRepository;

    @GetMapping("/{id}")
    public String getLog(@PathParam("id") UUID logId) {
        return logEntryRepository.getLog(logId)
                .map(LogEntry::getMessage)
                .orElseThrow(() -> MessageLoggerException.notFound(logId));
    }

    @GetMapping
    public List<LogEntry> getLogs() {
        return logEntryRepository.getLogs();
    }

    @PostMapping
    public ResponseEntity<LogEntry> addLog(@RequestBody @Valid @NonNull LogEntryRequest request) {
        return ResponseEntity.ok(logEntryRepository.addLog(request));
    }

}
