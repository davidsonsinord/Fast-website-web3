package com.davidson.service;

import com.davidson.model.SubDomain;
import com.davidson.repository.SubDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for subdomain
 */
@Service
public class SubDomainService {

    @Autowired
    private SubDomainRepository subDomainRepository;

    /**
     * Returns all domains in the database
     * @return domains list present in database
     */
    public List<SubDomain> findAll() {
        return subDomainRepository.collectAll();
    }

    /**
     * Delete subdomain in database
     * @param subDomain to be deleted
     */
    public void deleteSubDomain(SubDomain subDomain){
        subDomainRepository.delete(subDomain);
    }

}
