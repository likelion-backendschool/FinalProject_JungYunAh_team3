package com.example.app.config;

import com.example.app.domain.Member;
import com.example.app.exception.ErrorType;
import com.example.app.exception.MemberException;
import com.example.app.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
UserDetailsService
- DB 데이터를 가져오는 역할
 */
@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberException(ErrorType.NOT_FOUND));

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(String.valueOf(member.getRole())));

    return new MemberContext(member, authorities);
  }
}
