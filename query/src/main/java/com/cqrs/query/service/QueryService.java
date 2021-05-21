package com.cqrs.query.service;

import com.cqrs.query.entity.HolderAccountSummary;
import com.cqrs.query.loan.LoanLimitResult;
import java.util.List;
import reactor.core.publisher.Flux;

public interface QueryService {
  void reset();
  HolderAccountSummary getAccountInfo(String holderId);
  Flux<HolderAccountSummary> getAccountInfoSubscription(String holderId);
  List<LoanLimitResult> getAccountInfoScatterGather(String holderId);
}
