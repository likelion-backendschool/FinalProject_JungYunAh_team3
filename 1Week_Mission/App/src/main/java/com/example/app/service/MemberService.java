package com.example.app.service;

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

    sendEmail(member);
  }

  private void sendEmail(Member member) {
    String subject = "[멋북스] 회원 가입해주셔서 감사합니다.";
    String text = "안녕하세요. %s님! 멋북스에 오신 것을 환영합니다.<br/>".formatted(member.getNickname());

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
}
