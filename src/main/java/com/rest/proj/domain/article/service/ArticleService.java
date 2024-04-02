package com.rest.proj.domain.article.service;

import com.rest.proj.domain.article.DTO.ArticleForm;
import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService{
    private final ArticleRepository articleRepository;

    public Optional<Article> getArticle(Long id) {
        return this.articleRepository.findById(id);

    }

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Article create(String title, String content) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();
        this.articleRepository.save(article);
        return article;
    }

}
