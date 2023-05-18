package com.project.boardsideproject.repository;

import com.project.boardsideproject.domain.Article;
import com.project.boardsideproject.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// 1) QuerydslPredicateExecutor : 기본적으로 엔티티 필드에 대한 모든 검색 기능 추가
// ㄴ exact match 로만 검색
// ㄴ like query -> 검색엔진
// ㄴ db 엔진에게 위임
// 2) QuerydslBinderCustomizer : db 엔진에게 부분 검색 기능을 위임
@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {

    // java 8 이후, interface 구현 가능 (default 키워드)
    // 인터페이스만 가지고 모든 기능을 사용할 거기 때문에 해당 뎁스에서 구현
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // 선택적인 필드만 필터 검색을 하고 싶을 경우
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title,root.content, root.hashtag, root.createdAt, root.createdBy);

        // exact match rule change
        // 1. SimpleExpression, 2. StringExpression
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}

