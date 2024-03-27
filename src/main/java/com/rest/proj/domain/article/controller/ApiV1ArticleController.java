package com.rest.proj.domain.article.controller;

import com.rest.proj.domain.article.DTO.ArticleForm;
import com.rest.proj.domain.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    @GetMapping("")
    public List<Article> getArticles(){
        List<Article> articles = new ArrayList<>();
        articles.add(new Article((1L), "title1"));
        articles.add(new Article((2L), "title1"));
        articles.add(new Article((3L), "title1"));

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Long id){
        Article article = new Article((id), "randomTitle");
        return article;
    }

    // Post
    @PostMapping("")
    public String createArticle(ArticleForm articleForm){
        Article article = new Article((777L), articleForm.getTitle());
        return String.format("articleId: %s /n article.title: %s", article.getId(), article.getTitle());
    }

    @PatchMapping("/{id}")
    public Article modifyArticle(@PathVariable("id") Long id, ArticleForm articleForm){
        Article article = new Article((id), "preModifyTitle");
        Article article1 = article.toBuilder()
                .title(articleForm.getTitle())
                .build();
        return article1;
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable("id") Long id){
//        Article article = new Article((id), "iDon'tWantToBeDeleted");

        return String.format("Article no.%s Deleted", id);
    }

}
