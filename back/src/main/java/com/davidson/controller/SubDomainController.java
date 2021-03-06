package com.davidson.controller;

import com.davidson.model.SubDomain;
import com.davidson.service.SubDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for subdomain
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "SubDomain")
public class SubDomainController {

    @Autowired
    private SubDomainService subDomainService;

    /**
     * Allows to recover all subdomains
     * @return subdomain list and display response in format JSON
     */
    @GetMapping("/subdomains")
    @ApiOperation("Allows to recover all subdomains")
    public List<SubDomain> collectAll() {
        return subDomainService.findAll();
    }

}
