package com.davidson.scheduledtask;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Skill;
import com.davidson.service.ProcessFormationsService;
import com.davidson.service.ScanFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Scheduled task that updates the database according to the files present in /resources/main
 */
@Component
public class ScheduledFormationsTask {

    @Autowired
    ScanFormationService scanFormationService;

    @Autowired
    ProcessFormationsService processFormationsService;

    @Value("${local.srcFolder}")
    private String dataPath;

    /**
     * process called every x time
     */
    @Scheduled(fixedRate = 10000) //TODO: parameterize refresh rate
    public void update() throws PathIsNotDirectoryException, IOException {
        Map<String, Map<String, Map<String, Skill>>> domainsMap = scanFormationService.mapDomain(dataPath);
        processFormationsService.processMain(domainsMap);
    }
}
