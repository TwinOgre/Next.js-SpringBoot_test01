package com.rest.proj.global.initData;

import com.rest.proj.domain.article.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(ArticleService articleService) {
        return args -> {
            articleService.create("titleNo.1", "contentNo.1");
            articleService.create("titleNo.2", "contentNo.2");
            articleService.create("titleNo.3", "contentNo.3");
            articleService.create("titleNo.4", "contentNo.4");
            articleService.create("titleNo.5", "contentNo.5");
        };
    }
}
