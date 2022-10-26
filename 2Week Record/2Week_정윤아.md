
## Title: [2Week] 정윤아

### 미션 요구사항 분석 & 체크리스트

- [x] 장바구니 품목 추가 및 삭제
- [x] 장바구니 품목 리스트
- [x] 주문 생성 및 취소
- [x] 주문 리스트 및 상세
- [x] 토스페이먼츠 결제 처리
- [x] 소유 캐시로 결제
- [x] MyBook으로 결제된 도서 기록
- [x] CashLog로 캐시 관련 기록
- [x] 결제 후 10분 이내 환불
- [ ] 디테일한 체크 로직 필요함
- [ ] 토스페이먼츠 결제 오류 

<br>

### 2주차 미션 요약

**[접근 방법]**

1주차 해설 코드에서 뒤를 이어 개발을 진행함

1. 장바구니

Member와 Product ManyToOne 관계 매핑

존재하는 Product인지 체크 후, builder를 통해 CartItem 생성

<br>

장바구니 리스트를 보여줄 때, Product의 정보가 필요하게 됨

Getter를 통해 Product를 가지고 올 수 있지만, QueryDsl를 한 번 이용해보고자

innerJoin을 통해 product 정보도 함께 조회해오도록 하였고,

OrderDto에 필요한 정보들을 모두 담아서 장바구니 리스트 페이지에 띄워질 수 있도록 함

<br>

삭제도 마찬가지로, 존재하는 Product인지, 해당 Product가 장바구니에 담겨있는지 체크 후 삭제 처리함

<br>

2. 주문

해당하는 Member의 장바구니에 담겨있는 모든 CartItem을 조회

각 CartItem과 연결된 Product를 조회해온 후, OrderItemList에 담아준다.

그 후 주문(Order)를 builder로 생성해주며, orderItemList와 name을 넣어주고 생성

<br>

주문 취소 시, 아직 결제를 하지 않은 상태(READY)인 경우, CANCEL로 변경

결제를 한 상태(DONE)인 경우, 

<br>

토스 페이먼츠를 이용하여 결제가 진행될 수 있도록 하였다.

보유하고 있는 예치금을 사용하면, 예치금만으로도 결제를 진행할 수 있다.

<br>


3. MyBook

주문이 결제가 완료되며, MyBook에 결제된 OrderItemList에서 Product를 getter로 가져와 기록함

MyBookDto를 통해, Product의 정보를 담아서 리스트 페이지에 띄워지도록 함

<br>

4. CashLog

Enum 타입의 EventType과 함께 충전, 결제, 환불, 정산, 환전된 금액과 회원을 기록함

주문이 결제될 때, 충전과 결제 기록이 연달아서 이루어짐

예비로 구현해둔 예치금 충전 시, 충전된 CashLog가 기록될 수 있도록 함

<br>

**[특이사항]**

Refactoring )

orElseThrow(null)을 자주 이용해주었는데, 1주차 때 적용해준 Exception을 재적용하면 좋을 것 같다.

예치금 충전 시, 결제되는 기능으로 연결 (지금은 결제 진행하지 않고 충전이 되도록 해두었음)

예치금으로 결제하는 기능 개발 후, 토스페이먼츠 결제 기능이 안되는 문제 해결하기
