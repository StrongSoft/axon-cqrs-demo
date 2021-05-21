package com.cqrs.jeju.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class AccountCreationEvent {
  private final String AccountID;
  private final Long balance;
}
