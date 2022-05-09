package com.davidson.service;

import com.davidson.model.Testimony;
import com.davidson.repository.TestimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Testimony
 */

@Service
public class TestimonyService {

    @Autowired
    private TestimonyRepository testimonyRepository;

    /**
     * Returns all testimonies in the database
     * @return testimonies list present in database
     */
    public List<Testimony> findAll() {
        return testimonyRepository.findAll();
    }

    /**
     * Create testimony in database
     * @param testimony to be created
     * @return testimony create with id
     */
    public Testimony createTestimony(Testimony testimony) {
        return testimonyRepository.save(testimony);
    }

    /**
     * Delete testimony in database
     * @param testimony to be deleted
     */
    public void deleteTestimony(Testimony testimony) {
        testimonyRepository.delete(testimony);
    }

    /**
     * Update testimony in database
     * @param testimony to be updated
     * @return testimony update
     */
    public Testimony updateTestimony(Testimony testimony) {
        return testimonyRepository.save(testimony);
    }
}
