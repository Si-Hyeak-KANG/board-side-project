package com.project.boardsideproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10_000)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    /*
    cascade => 엔티티끼리 강합 결합을 만들어서 실무에서는 잘 안쓰는 경우가 많음
    => 원치않는 데이터 소실이 발생할 수도 있음
    => 논리상으로는 게시글이 제거되면, 댓글도 제거되는게 맞지만,
    => 운영상 댓글 데이터 백업이 필요할 도 있음
     */
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude // 순환참조가 발생 -> 끊어줌
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // Factory 메서드 : new 키워드 없이 제공,
    // Article을 만들기 위해 필요한 값 의도 전달
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        // Article article = (Article) o;
        // ㄴ Java 14 pattern matching
        // ㄴ 위에 (o instanceof Article article) 을 통해 바로 타입 캐스팅
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
