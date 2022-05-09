package com.davidson.service;

import com.davidson.model.Skill;
import com.davidson.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for skill
 */
@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    /**
     * delete relation ship between subdomain and skill
     * @param skill to be deleted relation ship
     */
    public void deleteSkill(Long skill) {
        skillRepository.deleteSkill(skill);
    }

    public void updateSkill(Skill skill) {
        skillRepository.save(skill);
    }

    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

}
