package com.davidson.controller;

import com.davidson.service.ArticleService;
import com.davidson.model.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for article
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Allows to recover all articles
     * @param title of the field sought
     * @return the article with the title equal to the parameter ou list of articles present
     */
    @GetMapping("/posts")
    @ApiOperation("Allows to recover all articles")
    public ResponseEntity findAllOrFindByTitle(@RequestParam(required = false) String title) {
        if(title != null) {
            Article article = articleService.findByTitle(title);
            if(article != null){
                return ResponseEntity.ok(article);
            }
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(articleService.findAll());
        }
    }
}