package com.cqrs.command.commands;

import com.cqrs.command.dto.TransferDTO;
import com.cqrs.command.transfer.JejuBankCancelTransferCommand;
import com.cqrs.command.transfer.JejuBankCompensationCancelCommand;
import com.cqrs.command.transfer.JejuBankTransferCommand;
import com.cqrs.command.transfer.SeoulBankCancelTransferCommand;
import com.cqrs.command.transfer.SeoulBankCompensationCancelCommand;
import com.cqrs.command.transfer.SeoulBankTransferCommand;
import com.cqrs.command.transfer.factory.TransferCommandFactory;
import java.util.UUID;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@ToString
@Getter
public class MoneyTransferCommand {
  private final String srcAccountID;
  @TargetAggregateIdentifier
  private final String dstAccountID;
  private final Long amount;
  private final String transferID;
  private final BankType bankType;

  public enum BankType{
    JEJU(command -> new TransferCommandFactory(new JejuBankTransferCommand(), new JejuBankCancelTransferCommand(), new JejuBankCompensationCancelCommand())),
    SEOUL(command -> new TransferCommandFactory(new SeoulBankTransferCommand(), new SeoulBankCancelTransferCommand(), new SeoulBankCompensationCancelCommand()));

    private final Function<MoneyTransferCommand, TransferCommandFactory> expression;

    BankType(
        Function<MoneyTransferCommand, TransferCommandFactory> expression) {
      this.expression = expression;
    }

    public TransferCommandFactory getCommandFactory(MoneyTransferCommand command){
      TransferCommandFactory factory = expression.apply(command);
      factory.create(command.getSrcAccountID(), command.getDstAccountID(), command.amount, command.getTransferID());
      return factory;
    }
  }

  public static MoneyTransferCommand of(TransferDTO transferDTO){
    return MoneyTransferCommand.builder()
        .srcAccountID(transferDTO.getSrcAccountID())
        .dstAccountID(transferDTO.getDstAccountID())
        .amount(transferDTO.getAmount())
        .bankType(transferDTO.getBankType())
        .transferID(UUID.randomUUID().toString())
        .build();
  }
}
