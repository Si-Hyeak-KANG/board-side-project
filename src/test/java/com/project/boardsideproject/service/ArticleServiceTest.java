package com.project.boardsideproject.service;

import com.project.boardsideproject.domain.Article;
import com.project.boardsideproject.domain.type.SearchType;
import com.project.boardsideproject.dto.ArticleDto;
import com.project.boardsideproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

// Spring Boot Application X, Mockito 사용 : dependency Mocking
@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    /**
     * SUT(System Under Test) : 테스트 대상
     */
    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticles() {

        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글 반환한다.")
    @Test
    void givenId_whenSearchingArticle_thenReturnsArticle() {

        ArticleDto article = sut.searchArticle(1L); // 제목, 본문, ID, 닉네임, 해시태그

        assertThat(article).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void createTest() throws Exception {

        given(articleRepository.save(any(Article.class))).willReturn(null);

        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "james", "title", "content", "hashtag");
        sut.saveArticle(dto);

        then(articleRepository).should().save(any(Article.class));
    }
}