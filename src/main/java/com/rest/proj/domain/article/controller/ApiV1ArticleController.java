package com.rest.proj.domain.article.controller;

import com.rest.proj.domain.article.DTO.ArticleForm;
import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final List<Article> articles;
    }

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles(){
        List<Article> articles = this.articleService.getList();


        return RsData.of("S-1", "성공", new ArticlesResponse(articles));
    }

    @AllArgsConstructor
    @Getter
    public static class ArticleResponse {
        private final Article article;
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id){
        return this.articleService.getArticle(id).map(article -> RsData.of(
                "S-1",
                "성공",
                new ArticleResponse(article)
        )).orElseGet(() -> RsData.of(
                "F-1",
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }

    // Post
//    @PostMapping("")
//    public String createArticle(ArticleForm articleForm){
//        Article article = new Article( articleForm.getTitle(), articleForm.getContent());
//        this.articleService.create(articleForm.getTitle(), articleForm.getContent());
//        return String.format("%s 게시글 저장이 완료됐습니다.", article.getTitle());
//    }

    @Getter
    public static class WriteRequest {
        private String title;
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class WriteResponce {
        private final  Article article;
    }
    @PostMapping("")
    public RsData<WriteResponce> write(@Valid @RequestBody WriteRequest writeRequest){
        Article article = this.articleService.create(writeRequest.getTitle(), writeRequest.getContent());
        return RsData.of(
                "S-1",
                "등록이 완료 됐습니다.",
                new WriteResponce(article)
                );
    }
    @Getter
    public static class ModifyRequest {
        private String title;
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class ModifyResponce {
        private final  Article article;
    }

    @PatchMapping("/{id}")
    public RsData<ModifyResponce> modifyArticle(@PathVariable("id") Long id, @Valid @RequestBody ModifyRequest modifyRequest){
        Article modifyArticle =this.articleService.modify(id, modifyRequest.getTitle(), modifyRequest.getContent());

        return RsData.of(
                "S-1",
                "수정이 완료 됐습니다.",
                new ModifyResponce(modifyArticle)
        );
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteResponce {
        private final  Long articleId;
    }
    @DeleteMapping("/{id}")
    public RsData<DeleteResponce> deleteArticle(@PathVariable("id") Long id){
        this.articleService.deleteByArticleId(id);

        return RsData.of(
                "S-1",
                "삭제가 성공했습니다."

        );
    }

}
