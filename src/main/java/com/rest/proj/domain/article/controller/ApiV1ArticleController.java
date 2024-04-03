package com.rest.proj.domain.article.controller;

import com.rest.proj.domain.article.DTO.ArticleForm;
import com.rest.proj.domain.article.entity.Article;
import com.rest.proj.domain.article.service.ArticleService;
import com.rest.proj.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Data
    public static class WriteRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class WriteResponce {
        private final  Article article;
    }


    @PostMapping("")
    public RsData<WriteResponce> write(@RequestBody WriteRequest writeRequest){
        RsData<Article> writeRs = this.articleService.create(writeRequest.getTitle(), writeRequest.getContent());
        if(writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriteResponce(writeRs.getData())
                );
    }
    @Data
    public static class ModifyRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class ModifyResponce {
        private final  Article article;
    }

    @PatchMapping("/{id}")
    public RsData<ModifyResponce> modifyArticle(@PathVariable("id") Long id, @Valid @RequestBody ModifyRequest modifyRequest){
        Optional<Article> optionalArticle = this.articleService.findById(id);
        if(optionalArticle.isEmpty()){
            return RsData.of(
                    "F-4",
                    "%s번 게시물은 존재하지 않습니다.".formatted(id),
                    null
            );
        }
        // 회원 권한 체크 canModify();

        RsData<Article> articleRsData =this.articleService.modify(optionalArticle.get(), modifyRequest.getTitle(), modifyRequest.getContent());

        return RsData.of(
                articleRsData.getResultCode(),
                articleRsData.getMsg(),
                new ModifyResponce(articleRsData.getData())
        );
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteResponce {
        private final  Article article;
    }
    @DeleteMapping("/{id}")
    public RsData<DeleteResponce> deleteArticle(@PathVariable("id") Long id){
        Optional<Article> optionalArticle = this.articleService.findById(id);
        if(optionalArticle.isEmpty()){
            return RsData.of(
                    "F-5",
                    "%d번 게시글이 없어 삭제 실패했습니다.".formatted(id),
                    null
            );
        }
        RsData<Article> deletedRsData = this.articleService.deleteByArticle(optionalArticle.get());
        return RsData.of(
                deletedRsData.getResultCode(),
                deletedRsData.getMsg(),
                new DeleteResponce(deletedRsData.getData())
        );
    }

}
