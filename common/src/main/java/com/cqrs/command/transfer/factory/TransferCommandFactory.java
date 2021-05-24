package com.cqrs.command.transfer.factory;

import com.cqrs.command.transfer.AbstractCancelTransferCommand;
import com.cqrs.command.transfer.AbstractCompensationCancelCommand;
import com.cqrs.command.transfer.AbstractTransferCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransferCommandFactory {
  private final AbstractTransferCommand transferCommand;
  private final AbstractCancelTransferCommand abortTransferCommand;
  private final AbstractCompensationCancelCommand compensationAbortCommand;

  public void create(String srcAccountID, String dstAccountID, Long amount, String transferID){
    transferCommand.create(srcAccountID, dstAccountID, amount, transferID);
    abortTransferCommand.create(srcAccountID, dstAccountID, amount, transferID);
    compensationAbortCommand.create(srcAccountID, dstAccountID, amount, transferID);
  }

  public AbstractTransferCommand getTransferCommand(){
    return transferCommand;
  }
  public AbstractCancelTransferCommand getAbortTransferCommand(){return abortTransferCommand;}
  public AbstractCompensationCancelCommand getCompensationAbortCommand(){return compensationAbortCommand;}
}
