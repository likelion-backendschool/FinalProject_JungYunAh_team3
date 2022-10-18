package com.example.app.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
글 (Post)
- author 작성자
- subject 글 제목
- content 글 내용
- contentHtml HTML 렌더링
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post extends BaseEntity{

  @ManyToOne
  @JoinColumn(name = "authorId")
  private Member author;

  private String subject;

  private String content;

  private String contentHtml;
}
