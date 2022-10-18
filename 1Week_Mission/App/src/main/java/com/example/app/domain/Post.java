package com.example.app.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*
글 (Post)
- author 작성자
- subject 글 제목
- content 글 내용
- contentHtml HTML 렌더링
 */
public class Post extends BaseEntity{

  @ManyToOne
  @JoinColumn(name = "USER_UUID")
  private Member author;

  private String subject;

  private String content;

  private String contentHtml;
}
