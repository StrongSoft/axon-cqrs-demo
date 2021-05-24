package com.cqrs.events.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CompletedCancelTransferEvent {
  private final String srcAccountID;
  private final String dstAccountID;
  private final Long amount;
  private final String transferID;
}
