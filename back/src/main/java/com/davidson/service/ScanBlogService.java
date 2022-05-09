package com.davidson.service;

import com.davidson.exception.PathIsNotDirectoryException;
import com.davidson.model.Article;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that will be called every x time by ScheduledTask
 * Service that scans the files in /resources/domains and creates a map with the corresponding domains, sub-domains and skills
 */
@Service
@Slf4j
public class ScanBlogService {

    @Value("${git.speakerURI}")
    private String gitSpeakerUri;

    @Value("${local.srcFolder}")
    private String dataPath;

    @Autowired
    ParserArticleService parserArticleService;

    /**
     * scan the folder /dataBlog and return a domain map with a subdomain map and a skill map
     *
     * @param path path to the local data folder
     * @return list of articles
     * @throws IOException                 Exception for file manipulation
     * @throws PathIsNotDirectoryException Exception in case of a file being process instead of a folder
     */
    public List<Article> getArticles(String path) throws IOException, PathIsNotDirectoryException {
        List<Article> articles = new ArrayList<>();
        boolean pull = this.gitPull(path + "dataBlog");
        if (!pull) {
            this.gitClone(path + "dataBlog");
            return articles;
        } else {
            File resourcesFile = new File(path + "dataBlog/Articles");
            if (!resourcesFile.isDirectory()) {
                throw new PathIsNotDirectoryException();
            }
            File[] articlesFile = resourcesFile.listFiles();

            for (File articleFile : articlesFile) {
                if (!articleFile.getName().equals(".git")) {
                    for (File file : articleFile.listFiles()) {
                        if (file.getName().contains(".md")) {
                            String artPath = articleFile.getPath().replace(dataPath, "/usr/");
                            JsonObject jsonArticle = parserArticleService.jsonParser(file, artPath);
                            Article article = Article.builder()
                                    .path(jsonArticle.get("path").getAsString())
                                    .title(jsonArticle.get("title").getAsString())
                                    .date(jsonArticle.get("date").getAsString())
                                    .author(jsonArticle.get("author").getAsString())
                                    .cover(jsonArticle.get("cover").getAsString())
                                    .markdown(jsonArticle.get("markdown").getAsString())
                                    .build();
                            articles.add(article);
                        }
                    }

                }
            }
        }
        return articles;
    }


    /**
     * performs a clone at the first execution of the task to retrieve the content
     *
     * @param path
     * @return
     */
    private void gitClone(String path) {
        try {
            Git.cloneRepository().setURI(gitSpeakerUri)
                    .setDirectory(new File(path))
                    .call();
            log.info("CLONE SUCCESS ");
        } catch (GitAPIException ex) {
            log.error("ERROR CLONE", ex);
        }
    }

    /**
     * performs a git pull to retrieve training changes
     *
     * @param path
     * @return
     */
    public boolean gitPull(String path) {
        try {
            Repository localRepo = new FileRepositoryBuilder().setGitDir(new File(path + "/.git")).build();
            Git git = new Git(localRepo);
            PullCommand pullCmd = git.pull();
            pullCmd.call();
            log.info("PULL SUCCESS IN " + git.getRepository().getBranch());
        } catch (GitAPIException | IOException ex) {
            log.error("ERROR PULL" + ex);
            return false;
        }
        return true;
    }
}
