package com.cqrs.events.transfer;

import com.cqrs.command.transfer.factory.TransferCommandFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class MoneyTransferEvent {
  private final String srcAccountID;
  private final String dstAccountID;
  private final Long amount;
  private final String transferID;
  private final TransferCommandFactory commandFactory;
}
