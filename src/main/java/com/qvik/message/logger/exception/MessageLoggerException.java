package com.qvik.message.logger.exception;

import java.util.UUID;

public class MessageLoggerException extends RuntimeException {

    public MessageLoggerException(String message) {
        super(message);
    }

    public static MessageLoggerException notFound(UUID logId) {
        return new MessageLoggerException(String.format("Log with id [%s] not found.", logId));
    }
}
