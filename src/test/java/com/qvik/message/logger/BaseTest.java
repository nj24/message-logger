package com.qvik.message.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qvik.message.logger.model.LogEntryRequest;
import com.qvik.message.logger.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

    protected static final ObjectMapper objectMapper = new ObjectMapper();
    public static final int MAX_AGE = 1000;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected LogEntryRepository logEntryRepository;


    protected void addLogMessage(String message, String source, Integer maxAge) throws Exception {
        LogEntryRequest request = LogEntryRequest.builder()
                .message(message)
                .source(source)
                .maxAge(maxAge)
                .build();

        mvc.perform(post("/logs")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.message == \"%s\" && @.source == \"%s\")]", message, source).exists());
    }
}
