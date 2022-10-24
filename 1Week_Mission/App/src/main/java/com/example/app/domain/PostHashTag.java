package com.example.app.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
글 태그[중간 테이블](PostHashTag)
- memberId 회원 번호
- postId 글 번호
- postKeywordId 글키워드번호
- index unique postId + postKeywordId
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(indexes = @Index(name = "index__postId__keywordId", columnList = "post_id, keyword_id", unique = true))
public class PostHashTag extends BaseEntity{

  private Long memberId;

  @ManyToOne
  private Post post;

  @ManyToOne
  private PostKeyword keyword;
}
