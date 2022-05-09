package com.davidson.service;

import com.davidson.model.Domain;
import com.davidson.model.Skill;
import com.davidson.model.SubDomain;
import com.davidson.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service that creates or deletes nodes from the database based on what is in the map returned by ScanFormationService
 */
@Service
@Slf4j
public class ProcessFormationsService {

    private static final String DELETED_MESSAGE = "{} IS DELETED";

    @Autowired
    private DomainService domainService;

    @Autowired
    private SubDomainService subDomainService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private TrainerService trainerService;

    /**
     * main process that will be called every x time by ScheduledTask
     * @param domainsMap
     */
    public void processMain(Map<String, Map<String, Map<String, Skill>>> domainsMap) {
        List<Domain> domainsInDataBase = domainService.findAll();
        this.processDelete(domainsMap, domainsInDataBase);
        this.processCreate(domainsMap, domainsInDataBase);
    }

    /**
     * process that manages the deletion of nodes in the database based on what is in /resources/domains
     * @param domainsMap
     * @param domainsInDataBase
     */
    public void processDelete(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(Domain domainDataBase : domainsInDataBase) {
            if(!domainsMap.containsKey(domainDataBase.getTitle())) {
                this.deleteDomainWithSubDomains(domainDataBase);
            }else {
                List<SubDomain> subDomainsInDataBase = domainDataBase.getSubdomains();
                if(subDomainsInDataBase != null) {
                    for (SubDomain subDomainDataBase : subDomainsInDataBase) {
                        this.deleteSubDomainsWithSkills(domainsMap.get(domainDataBase.getTitle()), subDomainDataBase);
                    }
                }
            }
        }
    }

    /**
     * process that manages the creation of nodes in the database based on what is in /resources/domains
     * @param domainsMap
     * @param domainsInDataBase
     */
    public void processCreate(Map<String, Map<String, Map<String, Skill>>> domainsMap, List<Domain> domainsInDataBase) {
        for(String titleDomain : domainsMap.keySet()) {
            boolean isPresentDomain = false;
            for(Domain domainInDatabase : domainsInDataBase) {
                if(titleDomain.equals(domainInDatabase.getTitle())) {
                    isPresentDomain = true;
                    this.updateSubDomains(domainsMap.get(titleDomain), domainInDatabase);
                }
            }
            if(!isPresentDomain) {
                List<SubDomain> subDomains = new ArrayList<>();
                for(String titleSubDomain : domainsMap.get(titleDomain).keySet()) {
                    List<Skill> skills = new ArrayList<>(domainsMap.get(titleDomain).get(titleSubDomain).values());
                    subDomains.add(SubDomain.builder().title(titleSubDomain).skills(skills).build());
                }
                domainService.createDomain(Domain.builder().title(titleDomain).icon("icon").subdomains(subDomains).build());
            }
        }
    }

    /**
     * updates subdomains if the domain is already present in the database based on what is in /resources/domains
     * @param subDomainsMap
     * @param domainInDatabase
     */
    public void updateSubDomains(Map<String, Map<String, Skill>> subDomainsMap, Domain domainInDatabase) {
        List<SubDomain> subDomainsInDatabase = domainInDatabase.getSubdomains();
        for(String titleSubDomain : subDomainsMap.keySet()) {
            boolean isPresentSubDomain = false;
            if(subDomainsInDatabase != null) {
                for (SubDomain subDomainInDatabase : subDomainsInDatabase) {
                    if (titleSubDomain.equals(subDomainInDatabase.getTitle())) {
                        isPresentSubDomain = true;
                        this.updateSkills(subDomainsMap.get(titleSubDomain), subDomainInDatabase, domainInDatabase);
                    }
                }
            }
            if(!isPresentSubDomain) {
                if (subDomainsInDatabase == null) {
                    subDomainsInDatabase = new ArrayList<>();
                    subDomainsInDatabase.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                } else {
                    subDomainsInDatabase.add(SubDomain.builder().title(titleSubDomain).skills(null).build());
                }
                domainInDatabase.setSubdomains(subDomainsInDatabase);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    /**
     * updates skills if the subdomain is already in the database based on what is in /resources/domains
     * @param skillsMap
     * @param subDomainInDatabase
     * @param domainInDatabase
     */
    public void updateSkills(Map<String, Skill> skillsMap, SubDomain subDomainInDatabase, Domain domainInDatabase) {
        List<Skill> skillsInDatabase = subDomainInDatabase.getSkills();
        for(Skill skill : skillsMap.values()) {
            boolean isPresentSkill = false;
            boolean modifiedSkill = false;
            if (skillsInDatabase != null) {
                for (Skill skillInDatabase : skillsInDatabase) {
                    if (skillInDatabase.getTitle().equals(skill.getTitle())) {
                        isPresentSkill = true;
                        if ((skillInDatabase.getDescription() == null && skill.getDescription() != null) || !skillInDatabase.getDescription().equals(skill.getDescription())) {
                            skillInDatabase.setDescription(skill.getDescription());
                            modifiedSkill = true;
                        }
                        if ((skillInDatabase.getPuces() == null && skill.getPuces() != null) || !skillInDatabase.getPuces().equals(skill.getPuces())) {
                            skillInDatabase.setPuces(skill.getPuces());
                            modifiedSkill = true;
                        }
                        if ((skillInDatabase.getLogo() == null && skill.getLogo() != null) || !skillInDatabase.getLogo().equals(skill.getLogo())) {
                            skillInDatabase.setLogo(skill.getLogo());
                            modifiedSkill = true;
                        }
                        skillInDatabase = updateTrainers(skillInDatabase, skill.getTrainers(), domainInDatabase);
                        if (modifiedSkill) {
                            skillService.updateSkill(skillInDatabase);
                        }
                    }
                }
            }

            if(!isPresentSkill) {
                if(skillsInDatabase == null) {
                    skillsInDatabase = new ArrayList<>();
                }
                skillsInDatabase.add(skill);
                subDomainInDatabase.setSkills(skillsInDatabase);
                domainService.updateDomain(domainInDatabase);
            }
        }
    }

    public Skill updateTrainers(Skill skillInDatabase, List<Trainer> trainers, Domain domainInDatabase) {
        List<Trainer> trainersInDatabase = skillInDatabase.getTrainers();
        if (trainersInDatabase == null) {
            trainersInDatabase = new ArrayList<>();
        }

        for (Trainer trainer : trainers) {
            boolean isPresentTrainer = false;
            for (Trainer trainerInDatabase : trainersInDatabase) {
                boolean modifiedTrainer;
                if (trainerInDatabase.getNom().equals(trainer.getNom())) {
                    isPresentTrainer = true;
                    modifiedTrainer = false;
                    if (!trainerInDatabase.getDescription().equals(trainer.getDescription())) {
                        trainerInDatabase.setDescription(trainer.getDescription());
                        modifiedTrainer = true;
                    }
                    if (!trainerInDatabase.getImage().equals(trainer.getImage())) {
                        trainerInDatabase.setImage(trainer.getImage());
                        modifiedTrainer = true;
                    }
                    if (modifiedTrainer)
                        trainerService.updateTrainer(trainerInDatabase);
                }
            }
            if (!isPresentTrainer) {
                trainersInDatabase.add(Trainer.builder().nom(trainer.getNom()).image(trainer.getImage()).description(trainer.getDescription()).build());
                skillInDatabase.setTrainers(trainersInDatabase);
                domainService.updateDomain(domainInDatabase);
            }
        }
        return skillInDatabase;
    }
    /**
     * deletes the domain and its subdomains if the domain is present in the database but not present in /resources/domains
     * @param domainInDatabase
     */
    public void deleteDomainWithSubDomains(Domain domainInDatabase) {
        List<SubDomain> subDomainsInDatabase = domainInDatabase.getSubdomains();
        if(subDomainsInDatabase != null) {
            for (SubDomain subDomainInDatabase : subDomainsInDatabase) {
                subDomainService.deleteSubDomain(subDomainInDatabase);
                log.info(DELETED_MESSAGE, subDomainInDatabase.getTitle());
            }
        }
        domainService.deleteDomain(domainInDatabase);
        log.info(DELETED_MESSAGE, domainInDatabase.getTitle());
    }

    /**
     * deletes the subdomain and its skills if the domain is ok and its subdomains are
     * present in the database but not present in /resources/domains
     * @param subDomainMap
     * @param subDomainInDatabase
     */
    public void deleteSubDomainsWithSkills(Map<String, Map<String, Skill>> subDomainMap, SubDomain subDomainInDatabase) {
        if (!subDomainMap.containsKey(subDomainInDatabase.getTitle())) {
            subDomainService.deleteSubDomain(subDomainInDatabase);
            log.info(DELETED_MESSAGE, subDomainInDatabase.getTitle());
        } else {
            List<Skill> skillsInDataBase = subDomainInDatabase.getSkills();
            if(skillsInDataBase != null) {
                for (Skill skillInDatabase : skillsInDataBase) {
                    if (!subDomainMap.get(subDomainInDatabase.getTitle()).containsKey(skillInDatabase.getTitle())) {
                        skillService.deleteSkill(skillInDatabase.getId());
                        log.info(DELETED_MESSAGE, skillInDatabase.getTitle());
                    }
                    List<Trainer> trainersInDataBase = skillInDatabase.getTrainers();
                    if(trainersInDataBase != null) {
                        for (Trainer trainer : trainersInDataBase)
                            if (!checkTrainersIsInTrainersListWithNullId(subDomainMap.get(subDomainInDatabase.getTitle()).get(skillInDatabase.getTitle()).getTrainers(), trainer)) {
                                trainerService.deleteTrainer(trainer.getId());
                                log.info(DELETED_MESSAGE, trainer.getNom());
                            }
                    }
                }

            }
        }
    }
    public boolean checkTrainersIsInTrainersListWithNullId(List<Trainer> trainerWithNull, Trainer trainerWithId) {
        for (Trainer trainerWithNullId : trainerWithNull){
            if(trainerWithNullId.getNom().equals(trainerWithId.getNom()))
                return true;
        }
        return false;
    }


    }
