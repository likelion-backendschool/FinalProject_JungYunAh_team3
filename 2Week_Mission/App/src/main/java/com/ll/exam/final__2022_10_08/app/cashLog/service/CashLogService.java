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
        .eventType(EventType.CHARGE)
        .build();
    CashLog payment = CashLog.builder()
        .member(rq.getMember())
        .price(calculatePayPrice)
        .eventType(EventType.PAYMENT)
        .build();
    cashLogRepository.save(charge);
    cashLogRepository.save(payment);
  }

  public CashLog addRestCash(Member member, long restCash) {
    CashLog cashLog = CashLog.builder()
        .member(member)
        .price((int) restCash)
        .eventType(EventType.CHARGE)
        .build();
    return cashLogRepository.save(cashLog);
  }

  public void refund(int calculatePayPrice) {
    CashLog cashLog = CashLog.builder()
        .member(rq.getMember())
        .price(calculatePayPrice)
        .eventType(EventType.REBATE_CHARGE)
        .build();
    cashLogRepository.save(cashLog);
  }
}
