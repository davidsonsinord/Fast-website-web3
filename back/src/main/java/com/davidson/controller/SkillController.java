package com.davidson.controller;

import com.davidson.model.Skill;
import com.davidson.service.SkillService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Controller for domain
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping(value = "/skill/{id}")
    public ResponseEntity findById(@PathVariable @NotNull Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if(skill.isPresent()){
            return ResponseEntity.ok(skill);
        }
        return ResponseEntity.noContent().build();
    }
}
