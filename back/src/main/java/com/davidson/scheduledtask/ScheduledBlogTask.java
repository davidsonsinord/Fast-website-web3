package com.davidson.scheduledtask;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Article;
import com.davidson.service.ProcessBlogService;
import com.davidson.service.ScanBlogService;
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
public class ScheduledBlogTask {

    private ProcessBlogService processBlogService;
    private ScanBlogService scanBlogService;

    @Value("${local.srcFolder}")
    private String dataPath;

    @Autowired
    public ScheduledBlogTask(ProcessBlogService processBlogService, ScanBlogService scanBlogService){
        this.scanBlogService = scanBlogService;
        this.processBlogService = processBlogService;
    }

    /**
     * process called every x time
     */
    @Scheduled(fixedRate = 10000) //TODO: parameterize refresh rate
    public void update() throws PathIsNotDirectoryException, IOException {
        List<Article> articles = scanBlogService.getArticles(dataPath);
        processBlogService.processMain(articles);
    }
}
