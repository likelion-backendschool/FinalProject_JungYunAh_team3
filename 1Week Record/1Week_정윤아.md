
## Title: [1Week] 정윤아

### 미션 요구사항 분석 & 체크리스트

- [x] 회원 가입
- [x] 로그인
- [x] 회원 가입 후 이메일 발송
- [x] 회원 정보 수정
- [x] 아이디 찾기
- [x] 글 등록
- [x] 글 리스트
- [x] 해시태그
- [ ] 글 상세 페이지
- [ ] 글 수정 및 삭제

<br>

### 1주차 미션 요약

**[접근 방법]**

1. 회원 가입 및 로그인

기존에 수업에서 배웠었던 Spring Security를 이용하여 개발 진행

Security에서의 User를 상속받아 MemberContext를 통해 회원(Member)의 필드를 활용할 수 있도록 구현

AuthenticationProvider를 상속받아 암호화된 DB에서의 비밀번호와 비교하여 인증할 수 있도록 함

toastr 플러그인을 활용하여, RequestDto에서 누락된 필드가 존재한다면 토스트 메시지가 띄워질 수 있도록 함

아이디, 이메일 중복 체크

<br>

2. 이메일 발송

Gmail SMTP를 yml 파일에 등록해주어 이메일을 전송

아이디 찾기의 경우, 입력된 이메일을 통해 아이디를 찾아 이메일을 전송

MimeMessage, MimeMessageHelper 이용

<br>

3. 글 등록 및 해시태그

Toast Editor 적용, markdown과 HTML 형태로 저장되도록 구현

Tagify를 적용하여 최대 10개까지의 태그를 추가할 수 있도록 함

String 형태로 받아온 해시태그를 Json으로 파싱하여 value값을 얻어 해시태그를 등록함

등록된 해시태그와 글(Post)을 중간 테이블에 저장

<br>

4. 글 리스트

메인 페이지에서 findAll으로 반환된 리스트를 모두 보여줌

<br>

5. Exception 처리

RuntimeException을 상속받아 CustomException을 생성하여, 도메인별 Exception에 활용함

enum 타입의 ErrorType으로 ErrorCode와 Message를 표현

<br>

**[특이사항]**

Refactoring )

아이디, 이메일이 중복될 경우에도 toastr로 경고문이 띄워질 수 있도록 비동기 처리하기

로그인 관련 핸들러 추가하기



