package com.project.boardsideproject.dto;

import com.project.boardsideproject.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.project.boardsideproject.domain.Article} entity
 */
public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,
        String hashtag
) implements Serializable {

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag()
        );
    }
}