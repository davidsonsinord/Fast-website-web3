package com.davidson.repository;

import com.davidson.model.Trainer;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for trainer
 */
@RepositoryRestResource(collectionResourceRel = "trainer", path = "trainer")
public interface TrainerRepository extends Neo4jRepository<Trainer, Long> {

    /**
     * delete relation ship between subdomain and skill
     * @param trainerId
     */
    @Query("MATCH (trainer) WHERE ID(trainer) = {trainerid} MATCH (skill:Skill)<-[r:TRAINER_IN]-(trainer) DELETE r")
    void deleteTrainer(@Param("trainerid") Long trainerId);

}
