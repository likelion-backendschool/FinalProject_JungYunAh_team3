package com.example.app.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinDto {

  @NotBlank(message = "아이디를 입력해주세요.")
  private String username;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password2;

  @NotBlank(message = "닉네임을 입력해주세요.")
  private String nickname;

  @Email
  @NotBlank(message = "이메일 입력해주세요.")
  private String email;

}
