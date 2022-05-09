package com.davidson.controller;

import com.davidson.service.TestimonyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for article
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Testimony")
public class TestimonyController {

    @Autowired
    private TestimonyService testimonyService;

    /**
     * Allows to recover all articles
     * @param title of the field sought
     * @return the article with the title equal to the parameter ou list of articles present
     */
    @GetMapping("/testimonies")
    @ApiOperation("Allows to recover all testimonies")
    public ResponseEntity findAll() {
            return ResponseEntity.ok(testimonyService.findAll());
        }
}