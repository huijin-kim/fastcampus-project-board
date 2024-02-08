package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA connect test")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("test")
    @Test
    void givenTestData_whenSelecting_tenWorksFine() {
        //given

        //when
        List<Article> articles = articleRepository.findAll();

        //work
        assertThat(articles)
                .isNotNull()
                .hasSize(123);    // classpath:test/ resources/data.sql 참조

    }

    
}