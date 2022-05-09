package com.davidson.service;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Skill;
import com.davidson.model.Trainer;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Service that will be called every x time by ScheduledTask
 * Service that scans the files in /resources/domains and creates a map with the corresponding domains, sub-domains and skills
 */
@Service
@Slf4j
public class ScanFormationService {

    @Value("${git.branch}")
    private String gitBranch;

    @Value("${git.formationURI}")
    private String gitFormationURI;

    private Gson gson = new GsonBuilder().create();

    private static final String TRAINER = "formateurs";
    private static final String LOGO = "logo";
    private static final String TITLE = "title";
    private static final String PUCES = "puces";


    /**
     * scan the folder /data and return a domain map with a subdomain map and a skill map
     *
     * @param path
     * @return
     */
    public Map<String, Map<String, Map<String, Skill>>> mapDomain(String path) throws IOException, PathIsNotDirectoryException {
        boolean pull = this.gitPull(path + "data");
        Map<String, Map<String, Map<String, Skill>>> mapDomains = new HashMap<>();
        if (pull) {
            File resourcesFile = new File(path + "data/content/courses");
            if (!resourcesFile.isDirectory()) {
                throw new PathIsNotDirectoryException();
            }
            File[] domainsFile = resourcesFile.listFiles();
            for (File domainFile : domainsFile) {
                if (!domainFile.getName().equals(".git")) {
                    Map<String, Map<String, Skill>> mapSubDomains = this.scanSubdomain(domainFile);
                    mapDomains.put(domainFile.getName(), mapSubDomains);
                }
            }
            return mapDomains;
        } else {
            this.gitClone(path);
            return mapDomains;
        }
    }

    /**
     * Scan the folder /data/{domainFile} and return a map with subdomain and skill
     *
     * @param domainFile
     * @return
     * @throws Exception
     */
    private Map<String, Map<String, Skill>> scanSubdomain(File domainFile) throws IOException {
        Map<String, Map<String, Skill>> mapSubDomains = new HashMap<>();
        File[] subDomainsFile = domainFile.listFiles();
        if (subDomainsFile != null) {
            for (File subDomainFile : subDomainsFile) {
                Map<String, Skill> mapSkill = this.scanSkill(subDomainFile);
                mapSubDomains.put(subDomainFile.getName(), mapSkill);
            }
        }
        return mapSubDomains;
    }

    /**
     * Scan the folder /data/{domainFile}/{subDomainFile} and return a map with skill
     *
     * @param subDomainFile
     * @return
     * @throws Exception
     */
    private Map<String, Skill> scanSkill(File subDomainFile) throws IOException {

        Map<String, Skill> mapSkill = new HashMap<>();
        File[] skillsFile = subDomainFile.listFiles();
        if (skillsFile != null) {
            for (File skillFile : skillsFile) {
                List<String> pucesVar = new ArrayList<>();
                List<Trainer> formateursVar = new ArrayList<>();
                String logoVar = "";
                if (!skillFile.isDirectory()) {
                    JsonObject jsonSkill = gson.fromJson(Files.toString(skillFile, Charsets.UTF_8), JsonObject.class);
                    if (jsonSkill.has(PUCES))
                        pucesVar = Arrays.asList(new Gson().fromJson(jsonSkill.getAsJsonArray(PUCES), String[].class));
                    if (jsonSkill.has(TRAINER))
                        formateursVar = Arrays.asList(new Gson().fromJson(jsonSkill.getAsJsonArray(TRAINER), Trainer[].class));
                    if (jsonSkill.has(LOGO))
                        logoVar = jsonSkill.get(LOGO).getAsString();
                    Skill skill = Skill.builder()
                            .title(jsonSkill.get(TITLE).getAsString())
                            .description(jsonSkill.get("description").getAsString())
                            .logo(logoVar)
                            .puces(pucesVar)
                            .trainers(formateursVar)
                            .build();
                    mapSkill.put(jsonSkill.get(TITLE).getAsString(), skill);
                } else {
                    File[] files = skillFile.listFiles();
                    for (File skillDirectory : files) {
                        if (skillDirectory.getName().contains(".json")) {
                            JsonObject jsonSkill = gson.fromJson(Files.toString(skillDirectory, Charsets.UTF_8), JsonObject.class);
                            if (jsonSkill.has(PUCES))
                                pucesVar = Arrays.asList(new Gson().fromJson(jsonSkill.getAsJsonArray(PUCES), String[].class));
                            if (jsonSkill.has(TRAINER))
                                formateursVar = Arrays.asList(new Gson().fromJson(jsonSkill.getAsJsonArray(TRAINER), Trainer[].class));
                            if (jsonSkill.has(LOGO))
                                logoVar = jsonSkill.get(LOGO).getAsString();
                            Skill skill = Skill.builder()
                                    .title(jsonSkill.get(TITLE).getAsString())
                                    .description(jsonSkill.get("description").getAsString())
                                    .logo(logoVar)
                                    .puces(pucesVar)
                                    .trainers(formateursVar)
                                    .build();
                            mapSkill.put(jsonSkill.get(TITLE).getAsString(), skill);
                        }
                    }
                }
            }
        }

        return mapSkill;
    }

    /**
     * performs a clone at the first execution of the task to retrieve the content
     *
     * @param path
     * @return
     */
    public boolean gitClone(String path) {
        try {
            Git.cloneRepository().setURI(gitFormationURI)
                    .setDirectory(new File(path + "data"))
                    .setBranchesToClone(Arrays.asList(gitBranch))
                    .setBranch(gitBranch)
                    .call();
            log.info("CLONE SUCCESS in {}", path + "data");
        } catch (GitAPIException ex) {
            log.error("ERROR CLONE {0}", ex);
            return false;
        }
        return true;
    }

    /**
     * performs a git pull to retrieve training changes
     *
     * @param path
     * @return
     */
    public boolean gitPull(String path) {
        try {
            Repository localRepo = new FileRepositoryBuilder().setGitDir(new File(path + "/.git")).build();
            Git git = new Git(localRepo);
            PullCommand pullCmd = git.pull();
            pullCmd.call();
            log.info("PULL SUCCESS IN {}", git.getRepository().getBranch());
        } catch (GitAPIException | IOException ex) {
            log.error("ERROR PULL {0}", ex);
            return false;
        }
        return true;
    }
}
