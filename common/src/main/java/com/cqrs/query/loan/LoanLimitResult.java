package com.cqrs.query.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Builder
public class LoanLimitResult {
  private final String holderID;
  private final String bankName;
  private final Long balance;
  private final Long loanLimit;
}
