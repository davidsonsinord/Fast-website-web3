package com.davidson.repository;

import com.davidson.model.Testimony;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for testimony
 */
@RepositoryRestResource(collectionResourceRel = "testimonies", path = "testimonies")
public interface TestimonyRepository extends Neo4jRepository<Testimony, Long> {
    List<Testimony> findAll();

}
