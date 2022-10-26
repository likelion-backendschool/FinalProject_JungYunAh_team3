package com.ll.exam.final__2022_10_08.app.cashLog.service;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cashLog.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.cashLog.entity.EventType;
import com.ll.exam.final__2022_10_08.app.cashLog.repository.CashLogRepository;
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
}
