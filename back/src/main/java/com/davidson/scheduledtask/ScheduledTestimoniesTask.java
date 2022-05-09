package com.davidson.scheduledtask;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Testimony;
import com.davidson.service.ProcessTestimoniesService;
import com.davidson.service.ScanTestimoniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Scheduled task that updates the database according to the files present in /resources/main
 */
@Component
public class ScheduledTestimoniesTask {

    @Autowired
    ScanTestimoniesService scanTestimoniesService;

    @Autowired
    ProcessTestimoniesService processTestimoniesService;

    @Value("${local.srcFolder}")
    private String dataPath;

    /**
     * process called every x time
     */
    @Scheduled(fixedRate = 10000) //TODO: parameterize refresh rate
    public void update() throws PathIsNotDirectoryException, IOException {
        List<Testimony> testimonies = scanTestimoniesService.getTestimonies(dataPath);
        processTestimoniesService.processMain(testimonies);
    }
}
