package com.cqrs.jeju.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.cqrs.command.transfer.JejuBankTransferCommand;
import com.cqrs.events.transfer.TransferApprovedEvent;
import com.cqrs.events.transfer.TransferDeniedEvent;
import com.cqrs.jeju.command.AccountCreationCommand;
import com.cqrs.jeju.event.AccountCreationEvent;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Entity
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Account {
  @AggregateIdentifier
  @Id
  private String accountID;
  private Long balance;

  @CommandHandler
  public Account(AccountCreationCommand command) throws IllegalAccessException{
    log.debug("handling {}", command);
    if(command.getBalance() <= 0){
      throw new IllegalAccessException("유효하지 않은 입력입니다.");
    }
    apply(new AccountCreationEvent(command.getAccountID(), command.getBalance()));
  }

  @EventSourcingHandler
  protected void on(AccountCreationEvent event){
    log.debug("event {}",event);
    accountID = event.getAccountID();
    balance = event.getBalance();
  }

  @CommandHandler
  protected void on(JejuBankTransferCommand command) throws InterruptedException {
    log.debug("handling {}", command);
    if(balance < command.getAmount()){
      apply(TransferDeniedEvent.builder()
      .srcAccountID(command.getSrcAccountID())
      .dstAccountID(command.getDstAccountID())
          .amount(command.getAmount())
          .description("잔고가 부족합니다.")
          .transferID(command.getTransferID())
          .build());
    } else {
      apply(TransferApprovedEvent.builder()
          .srcAccountID(command.getSrcAccountID())
          .dstAccountID(command.getDstAccountID())
          .amount(command.getAmount())
          .transferID(command.getTransferID())
          .build());
    }
  }

  @EventSourcingHandler
  protected void on(TransferApprovedEvent event){
    log.debug("event {}",event);
    balance -= event.getAmount();
  }
}
