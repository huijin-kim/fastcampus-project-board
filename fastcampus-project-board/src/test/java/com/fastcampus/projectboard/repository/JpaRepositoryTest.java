package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testdb")
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

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        //given
        long previousCount = articleRepository.count();

        //when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        //work
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);
        long previousCount = articleRepository.count();

        //when
//        Article savedArticle = articleRepository.save(article);   //예전에는 동일하게 존재하면 rollback하기에 update 쿼리가 안등장한다고한다. 하지만 나는 등장 했다.
        Article savedArticle = articleRepository.saveAndFlush(article);   //현재 버전에서 이가 차이점이 있나?

        //work
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long deletedCommentSize = article.getArticleComment().size();

        //when
        articleRepository.delete(article);

        //work
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }


}
