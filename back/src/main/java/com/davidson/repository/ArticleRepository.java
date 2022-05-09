package com.davidson.repository;

import com.davidson.model.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Repository for article
 */
@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleRepository extends Neo4jRepository<Article, Long> {

    /**
     * define query for search article with title equal to the parameter
     * @param title of the domain sought
     * @return
     */
    Article findByTitle(@RequestParam(required = false) String title);
    List<Article> findAll();
}
