package com.example.app.service;

import com.example.app.dto.request.MemberModifyDto;
import com.example.app.exception.ErrorType;
import com.example.app.exception.MemberException;
import com.example.app.domain.Member;
import com.example.app.domain.status.MemberRole;
import com.example.app.dto.request.JoinDto;
import com.example.app.repository.MemberRepository;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JavaMailSender javaMailSender;

  /*
  회원 가입
  - 아이디, 이메일 중복 체크
  - 비밀번호 일치 확인
  - 가입 축하 이메일 전송
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

    String subject = "[멋북스] 회원 가입해주셔서 감사합니다.";
    String text = "안녕하세요. %s님! 멋북스에 오신 것을 환영합니다.<br/>".formatted(member.getNickname());
    sendEmail(member, subject, text);
  }

  // 이메일 전송
  private void sendEmail(Member member, String subject, String text) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
      helper.setTo(member.getEmail());
      helper.setSubject(subject);
      helper.setText(text, true);
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  /*
  회원 정보 수정
  - 아이디, 이메일, 닉네임
  - 아이디, 이메일 중복 체크
   */
  public void modify(MemberModifyDto memberModifyDto, Member member) {

    if ((!member.getUsername().equals(memberModifyDto.getUsername()) && memberRepository.findByUsername(memberModifyDto.getUsername()).isPresent()) ||
    (!member.getEmail().equals(memberModifyDto.getEmail()) && memberRepository.findByEmail(memberModifyDto.getEmail()).isPresent())){
      throw new MemberException(ErrorType.DUPLICATED);
    }

    member.setUsername(memberModifyDto.getUsername());
    member.setNickname(memberModifyDto.getNickname());
    member.setEmail(memberModifyDto.getEmail());
    memberRepository.save(member);
  }

  /*
  아이디 찾기
  - 회원 이메일로 아이디 전송
   */
  public void findUsername(String email) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(ErrorType.NOT_FOUND));

    String subject = "[멋북스] 아이디 찾기";
    String text = "안녕하세요. %s님! 회원님의 아이디는 %s입니다.".formatted(member.getNickname(),
        member.getUsername());
    sendEmail(member, subject, text);
  }
}
