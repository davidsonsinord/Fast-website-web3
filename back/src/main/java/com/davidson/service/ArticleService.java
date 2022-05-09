package com.davidson.service;

import com.davidson.model.Article;
import com.davidson.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Article
 */

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Searches the article by title
     * @param title of the article sought
     * @return Article with equal title
     */
    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    /**
     * Returns all article in the database
     * @return articles list present in database
     */
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    /**
     * Create article in database
     * @param article to be created
     * @return article create with id
     */
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    /**
     * Delete article in database
     * @param article to be deleted
     */
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    /**
     * Update article in database
     * @param article to be updated
     * @return article update
     */
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }
}
