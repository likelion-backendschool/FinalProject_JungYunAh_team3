package com.example.app.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
BaseEntity
: 모든 Entity가 기본적으로 상속받아 사용하는 추상 클래스

- id 번호, PK
- createDate 등록날짜
- updateDate 갱신날짜
 */
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime updateDate;

  public BaseEntity(long id) {
    this.id = id;
  }
}
