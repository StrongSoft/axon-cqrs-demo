package com.cqrs.command.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class AbstractCancelTransferCommand {
  @TargetAggregateIdentifier
  protected String srcAccountID;
  protected String dstAccountID;
  protected Long amount;
  protected String transferID;

  public AbstractCancelTransferCommand create(String srcAccountID, String dstAccountID,
      Long amount, String transferID) {
    this.srcAccountID = srcAccountID;
    this.dstAccountID = dstAccountID;
    this.amount = amount;
    this.transferID = transferID;
    return this;
  }
}
