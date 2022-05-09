package com.davidson.service;

import com.davidson.model.Trainer;
import com.davidson.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for trainer
 */
@Service
public class TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    /**
     * delete relation ship between subdomain and skill
     * @param trainer to be deleted relation ship
     */
    public void deleteTrainer(Long trainer) {
        trainerRepository.deleteTrainer(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }
}
