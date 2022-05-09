package com.davidson.service;

import com.davidson.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that creates or deletes nodes from the database based on what is in the map returned by ScanBlogService
 */
@Service
@Slf4j
public class ProcessBlogService {

    @Autowired
    private ArticleService articleService;

    /**
     * main process that will be called every x time by ScheduledBlogTask
     *
     * @param articles
     */
    public void processMain(List<Article> articles) {
        List<Article> articlesInDataBase = articleService.findAll();
        this.processDelete(articles, articlesInDataBase);
        this.processCreate(articles, articlesInDataBase);
    }

    /**
     * process that manages the deletion of nodes in the database based on what is in /resources/articles
     *
     * @param articles
     * @param articlesInDataBase
     */
    public void processDelete(List<Article> articles, List<Article> articlesInDataBase) {
        boolean isPresentArticle = false;
        for (Article article : articles) {
            for (Article articleDatabase : articlesInDataBase) {
                if ((article.getTitle().equals(articleDatabase.getTitle()))) {
                    isPresentArticle = true;
                }
            }
            if (!isPresentArticle) this.deleteArticle(article);
        }
    }

    /**
     * deletes article if it is present in the database but not present in /resources/articles
     *
     * @param article
     */
    public void deleteArticle(Article article) {
        articleService.deleteArticle(article);
        log.info("{} IS DELETED", article.getTitle());
    }

    /**
     * process that manages the creation of nodes in the database based on what is in /resources/articles
     *
     * @param articles
     * @param articlesInDataBase
     */
    public void processCreate(List<Article> articles, List<Article> articlesInDataBase) {
        for (Article article : articles) {
            boolean isPresentArticle = false;
            for (Article articleDataBase : articlesInDataBase) {
                if ((article.getTitle().equals(articleDataBase.getTitle()))) {
                    isPresentArticle = true;
                    updateArticle(article, articleDataBase);
                }
            }
            if (!isPresentArticle) {
                log.info("==== path : {} ",article.getPath());
                articleService.createArticle(
                        Article.builder()
                                .path(article.getPath())
                                .title(article.getTitle())
                                .date(article.getDate())
                                .author(article.getAuthor())
                                .cover(article.getCover())
                                .markdown(article.getMarkdown())
                                .build());
            }
        }
    }

    public void updateArticle(Article article, Article articleInDatabase) {
        boolean modified = false;
        if (!article.getDate().equals(articleInDatabase.getDate())) {
            articleInDatabase.setDate(article.getDate());
            modified = true;
        }
        if (!article.getAuthor().equals(articleInDatabase.getAuthor())) {
            articleInDatabase.setAuthor(article.getAuthor());
            modified = true;
        }
        if (!article.getCover().equals(articleInDatabase.getCover())) {
            articleInDatabase.setCover(article.getCover());
            modified = true;
        }
        if (!article.getMarkdown().equals(articleInDatabase.getMarkdown())) {
            articleInDatabase.setMarkdown(article.getMarkdown());
            modified = true;
        }
        if (modified) {
            articleService.updateArticle(articleInDatabase);
        }
    }
}

