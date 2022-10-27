package com.ll.exam.final__2022_10_08.app.cashLog.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cashLog.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.cashLog.entity.EventType;
import com.ll.exam.final__2022_10_08.app.cashLog.repository.CashLogRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CashLogService {

  private final CashLogRepository cashLogRepository;
  private final Rq rq;

  public void successOrder(int calculatePayPrice) {
    CashLog charge = CashLog.builder()
        .member(rq.getMember())
        .price(calculatePayPrice)
        .eventType(EventType.충전)
        .build();
    CashLog payment = CashLog.builder()
        .member(rq.getMember())
        .price(calculatePayPrice)
        .eventType(EventType.결제)
        .build();
    cashLogRepository.save(charge);
    cashLogRepository.save(payment);
  }

  public CashLog updateRestCash(Member member, long restCash) {
    CashLog cashLog ;
    if (restCash > 0) {
      cashLog = CashLog.builder()
          .member(member)
          .price((int) restCash)
          .eventType(EventType.충전)
          .build();
    } else {
      cashLog = CashLog.builder()
          .member(member)
          .price((int) restCash)
          .eventType(EventType.결제)
          .build();
    }
    return cashLogRepository.save(cashLog);
  }

  public void refund(int calculatePayPrice) {
    CashLog cashLog = CashLog.builder()
        .member(rq.getMember())
        .price(calculatePayPrice)
        .eventType(EventType.환불_충전)
        .build();
    cashLogRepository.save(cashLog);
  }
}
