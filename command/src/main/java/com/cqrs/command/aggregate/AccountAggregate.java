package com.cqrs.command.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.cqrs.command.commands.AccountCreationCommand;
import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.MoneyTransferCommand;
import com.cqrs.command.commands.TransferApprovedCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.command.event.DepositCompletedEvent;
import com.cqrs.events.AccountCreationEvent;
import com.cqrs.events.DepositMoneyEvent;
import com.cqrs.events.WithdrawMoneyEvent;
import com.cqrs.events.transfer.MoneyTransferEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@RequiredArgsConstructor
@Aggregate
@Slf4j
public class AccountAggregate {
  @AggregateIdentifier
  private String accountID;
  private String holderID;
  private Long balance;

  @CommandHandler
  public AccountAggregate(AccountCreationCommand command){
    log.debug("handling {}", command);
    apply(new AccountCreationEvent(command.getAccountID(), command.getHolderID()));
  }

  @EventSourcingHandler
  protected void createAccount(AccountCreationEvent event){
    log.debug("applying {}", event);
    accountID = event.getAccountID();
    holderID = event.getHolderID();
    balance = 0L;
  }

  @CommandHandler
  public void depositMoney(DepositMoneyCommand command){
    log.debug("handling {}", command);
    if(command.getAmount() <= 0) {
      throw new IllegalStateException("amount >= 0");
    }
    apply(new DepositMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
  }

  @EventSourcingHandler
  protected void depositMoney(DepositMoneyEvent event){
    log.debug("applying {}", event);
    balance += event.getAmount();
  }

  @CommandHandler
  public void withdrawMoney(WithdrawMoneyCommand command){
    log.debug("handling {}", command);
    if (balance - command.getAmount() < 0){
      throw new IllegalStateException("잔고가 부족합니다.");
    } else if(command.getAmount() <= 0) {
      throw new IllegalStateException("amount >= 0");
    }
    apply(new WithdrawMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
  }

  @EventSourcingHandler
  protected void withdrawMoney(WithdrawMoneyEvent event){
    log.debug("applying {}", event);
    balance -= event.getAmount();
  }

  @CommandHandler
  private void transferMoney(MoneyTransferCommand command){
    log.debug("handling {}", command);
    apply(MoneyTransferEvent.builder()
        .srcAccountID(command.getSrcAccountID())
        .dstAccountID(command.getDstAccountID())
        .amount(command.getAmount())
        .commandFactory(command.getBankType().getCommandFactory(command))
        .transferID(command.getTransferID())
        .build());
  }

  @CommandHandler
  protected void transferMoney(TransferApprovedCommand command){
    log.debug("handling {}",command);
    apply(new DepositMoneyEvent(holderID, command.getAccountID(), command.getAmount()));
    apply(new DepositCompletedEvent(command.getAccountID(), command.getTransferID()));
  }
}
