package com.example.app.service;

import com.example.app.exception.ErrorType;
import com.example.app.exception.MemberException;
import com.example.app.domain.Member;
import com.example.app.domain.status.MemberRole;
import com.example.app.dto.request.JoinDto;
import com.example.app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  /*
  회원 가입
  - 아이디, 이메일 중복 체크
  - 비밀번호 일치 확인
   */
  public void join(JoinDto joinDto) {

    if (memberRepository.findByUsername(joinDto.getUsername()).isPresent()
        || memberRepository.findByEmail(joinDto.getEmail()).isPresent()) {
      throw new MemberException(ErrorType.DUPLICATED);
    }

    if (!joinDto.getPassword().equals(joinDto.getPassword2())) {
      throw new MemberException(ErrorType.MISMATCH);
    }

    Member member = Member.builder()
        .username(joinDto.getUsername())
        .password(passwordEncoder.encode(joinDto.getPassword()))
        .nickname(joinDto.getNickname())
        .email(joinDto.getEmail())
        .authLevel(3)
        .role(MemberRole.ROLE_MEMBER)
        .build();

    memberRepository.save(member);
  }
}
