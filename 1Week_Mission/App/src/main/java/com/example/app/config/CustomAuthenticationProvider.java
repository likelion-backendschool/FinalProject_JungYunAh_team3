package com.example.app.config;

import com.example.app.exception.ErrorType;
import com.example.app.exception.MemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
AuthenticationProvider 커스터마이징
- 암호화된 DB 데이터와 인증을 담당함
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(username);

    if (!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
      throw new MemberException(ErrorType.MISMATCH);
    }

    // 토큰 발급
    return new UsernamePasswordAuthenticationToken(memberContext.getMember(), null,
        memberContext.getAuthorities());
  }

  // 인증 객체가 토큰인 경우, TRUE
  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
