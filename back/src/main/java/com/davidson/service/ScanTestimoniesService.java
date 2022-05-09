package com.davidson.service;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Testimony;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.springframework.stereotype.Service;
import java.util.List;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Service that will be called every x time by ScheduledTask
 * Service that scans the files in /resources/domains and creates a map with the corresponding domains, sub-domains and skills
 */
@Service
public class ScanTestimoniesService {

    private Gson gson = new GsonBuilder().create();

    /**
     * scan the folder /dataBlog and return a domain map with a subdomain map and a skill map
     * @param path
     * @return
     */
    public List<Testimony> getTestimonies(String path) throws IOException, PathIsNotDirectoryException {
        List<Testimony> testimonies = new ArrayList<>();
            File resourcesFile = new File(path + "dataBlog/Testimonies");
            if (!resourcesFile.isDirectory()) {
                throw new PathIsNotDirectoryException();
            }
            File[] testimoniesFile = resourcesFile.listFiles();

            for(File testimonyFile : testimoniesFile) {
                        if (testimonyFile.getName().contains(".json")) {
                            JsonObject jsonTestimony = gson.fromJson(Files.toString(testimonyFile, Charsets.UTF_8), JsonObject.class);
                            Testimony testimony = Testimony.builder()
                                    .nom(jsonTestimony.get("nom").getAsString())
                                    .image(jsonTestimony.get("image").getAsString())
                                    .description(jsonTestimony.get("description").getAsString())
                                    .build();
                            testimonies.add(testimony);
                        }
                    }

        return testimonies;
    }
}
