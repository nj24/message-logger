package com.qvik.message.logger;

import com.qvik.message.logger.model.LogEntryRequest;
import org.awaitility.Duration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.awaitility.Awaitility.waitAtMost;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MessageLoggerTest extends BaseTest {

    @Test
    void shouldAddLogMessage() throws Exception {

        LogEntryRequest request = LogEntryRequest.builder()
                .message("Testing log messages")
                .source("Test")
                .maxAge(MAX_AGE)
                .build();

        String payload = objectMapper.writeValueAsString(request);

        mvc.perform(post("/logs")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.message == \"%s\" && @.source == \"%s\")]", "Testing log messages", "Test").exists());
    }

    @Test
    void shouldGetLogs() throws Exception {

        addLogMessage("Adding Message 1", "shouldGetLogs", MAX_AGE);
        addLogMessage("Adding Message 2", "shouldGetLogs", MAX_AGE);

        mvc.perform(get("/logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.message == \"%s\" && @.source == \"%s\")]", "Adding Message 1", "shouldGetLogs").exists())
                .andExpect(jsonPath("$.[?(@.message == \"%s\" && @.source == \"%s\")]", "Adding Message 2", "shouldGetLogs").exists());
    }

    @Test
    void shouldNotGetLogIfMaxAgePassed() throws Exception {

        final int size = logEntryRepository.getLogs().size();
        addLogMessage("Adding Message 1", "shouldGetLogs", MAX_AGE);
        addLogMessage("Adding Message 2", "shouldGetLogs", 1);

        waitAtMost(Duration.ONE_MINUTE).until(() -> logEntryRepository.getLogs().size() - size == 1);

        mvc.perform(get("/logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", Matchers.hasSize(size +1)))
                .andExpect(jsonPath("$.[?(@.message == \"%s\" && @.source == \"%s\")]", "Adding Message 2", "shouldGetLogs").doesNotExist());
    }


}
