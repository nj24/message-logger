package com.qvik.message.logger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitoringStatisticsTest  extends BaseTest{

    @Test
    void shouldGetLogStatistics() throws Exception {

        final int size = logEntryRepository.getLogs().size();

        addLogMessage("MonitoringStatistics Message Logging 1", "getLogStatistics",999);
        addLogMessage("MonitoringStatistics Message Logging 2", "getLogStatistics",1000);
        addLogMessage("MonitoringStatistics Message Logging 3", "getLogStatistics",200);
        addLogMessage("MonitoringStatistics Message Logging 4", "getLogStatistics",100);
        addLogMessage("MonitoringStatistics Message Logging 5", "getLogStatistics",500);


        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.totalNumberOfLogs == \"%s\" && @.maxAgeRange.startRange == \"%s\" && @.maxAgeRange.endRange == \"%s\")]",
                        size + 5,100,1000).exists());
    }
}
