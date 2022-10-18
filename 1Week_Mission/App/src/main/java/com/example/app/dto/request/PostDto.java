package com.example.app.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDto {
  
  @NotBlank(message = "제목을 입력해주세요.")
  private String subject;

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;

  @NotBlank(message = "내용을 입력해주세요.")
  private String contentHtml;

  @NotBlank(message = "해시태그를 입력해주세요.")
  private String hashTags;
}
