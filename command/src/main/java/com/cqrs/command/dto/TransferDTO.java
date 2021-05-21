package com.cqrs.command.dto;

import com.cqrs.command.commands.MoneyTransferCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
  private String srcAccountID;
  private String dstAccountID;
  private Long amount;
  private MoneyTransferCommand.BankType bankType;
}
