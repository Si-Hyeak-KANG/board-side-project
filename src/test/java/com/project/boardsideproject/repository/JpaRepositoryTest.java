package com.project.boardsideproject.repository;

import com.project.boardsideproject.config.JpaConfig;
import com.project.boardsideproject.domain.Article;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * slice test
 * CRUD 테스트
 */
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest // 메소드 단위로 트랜잭션
class JpaRepositoryTest {

    // JUnit5와 최신 SpringBoot 에서는 테스트에서도 생성자 주입 방식 사용 가능
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_wheSelecting_thenWorksFine() {
        List<Article> articles = articleRepository.findAll();
        assertThat(articles)
                .isNotNull()
                .hasSize(350);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_wheInserting_thenWorksFine() {
        
        long previousCount = articleRepository.count();

        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    // test transaction 의 기본은 rollback
    @DisplayName("update 테스트")
    @Test
    void givenTestData_wheUpdating_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    // test transaction 의 기본은 rollback
    @DisplayName("delete 테스트 - 게시글 삭제시, 댓글도 삭제되는가")
    @Test
    void givenTestData_wheDeleting_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }

}
