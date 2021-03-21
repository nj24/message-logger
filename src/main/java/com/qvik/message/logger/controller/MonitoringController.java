package com.qvik.message.logger.controller;

import com.qvik.message.logger.model.LogStats;
import com.qvik.message.logger.service.MonitoringStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MonitoringController {

    private MonitoringStatisticsService service;

    @Autowired
    public MonitoringController(MonitoringStatisticsService service) {
        this.service = service;
    }

    @GetMapping
    public LogStats getLogStatistics() {
        return service.getMonitoringStatistics();
    }

}
