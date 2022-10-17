package com.example.app.domain;

import com.example.app.domain.status.MemberRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
회원 (Member)
- username 로그인 아이디
- password 로그인 비밀번호
- nickname 필명
- email 이메일
- authLevel 권한 레벨(3 = 일반, 7 = 관리자)
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity{

  @Column(unique = true)
  private String username;

  @JsonIgnore
  private String password;

  private String nickname;

  @Column(unique = true)
  private String email;

  private int authLevel;

  @Enumerated(EnumType.STRING)
  private MemberRole role;

  public Member(long id) {
    super(id);
  }
}
