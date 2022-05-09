package com.davidson.service;

import com.davidson.model.Testimony;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that creates or deletes nodes from the database based on what is in the map returned by ScanBlogService
 */
@Service
@Slf4j
public class ProcessTestimoniesService {

    @Autowired
    private TestimonyService testimonyService;

    /**
     * main process that will be called every x time by ScheduledBlogTask
     * @param testimonies
     */
    public void processMain(List<Testimony> testimonies) {
        List<Testimony> testimoniesInDataBase = testimonyService.findAll();
        this.processDelete(testimonies, testimoniesInDataBase);
        this.processCreate(testimonies, testimoniesInDataBase);
    }

    /**
     * process that manages the deletion of nodes in the database based on what is in /resources/articles
     * @param testimonies
     * @param testimoniesInDataBase
     */
    public void processDelete(List<Testimony> testimonies, List<Testimony> testimoniesInDataBase) {
        boolean isPresentTestimony = false;
        for(Testimony testimony : testimonies){
            for(Testimony testimonyDatabase : testimoniesInDataBase) {
                if((testimony.getNom().equals(testimonyDatabase.getNom()))) {
                    isPresentTestimony = true;
                }
            }
            if(!isPresentTestimony) this.deleteTestimony(testimony);
        }
    }

    /**
     * deletes article if it is present in the database but not present in /resources/articles
     * @param testimony
     */
    public void deleteTestimony(Testimony testimony) {
        testimonyService.deleteTestimony(testimony);
        log.info("{} IS DELETED", testimony.getNom());
    }

    /**
     * process that manages the creation of nodes in the database based on what is in /resources/articles
     * @param testimonies
     * @param testimoniesInDataBase
     */
    public void processCreate(List<Testimony> testimonies, List<Testimony> testimoniesInDataBase) {
        for(Testimony testimony : testimonies){
            boolean isPresentTestimony = false;
            for(Testimony testimonyDatabase : testimoniesInDataBase) {
                if((testimony.getNom().equals(testimonyDatabase.getNom()))) {
                    isPresentTestimony = true;
                    updateTestimony(testimony, testimonyDatabase);
                }
            }
            if(!isPresentTestimony){
                testimonyService.createTestimony(
                        Testimony.builder()
                                .nom(testimony.getNom())
                                .image(testimony.getImage())
                                .description(testimony.getDescription())
                                .build());
            }
        }
    }

    public void updateTestimony(Testimony testimony, Testimony testimonyDatabase){
        boolean modified = false;
        if(!testimony.getNom().equals(testimonyDatabase.getNom())){
            testimonyDatabase.setNom(testimony.getNom());
            modified = true;
        }
        if(!testimony.getDescription().equals(testimonyDatabase.getDescription())){
            testimonyDatabase.setDescription(testimony.getDescription());
            modified = true;
        }
        if(!testimony.getImage().equals(testimonyDatabase.getImage())){
            testimonyDatabase.setImage(testimony.getImage());
            modified = true;
        }
        if(modified){
            testimonyService.updateTestimony(testimonyDatabase);
        }
    }
}


