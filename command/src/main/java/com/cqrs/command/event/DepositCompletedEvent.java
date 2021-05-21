package com.cqrs.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class DepositCompletedEvent {
  private final String accountID;
  private final String transferID;
}
