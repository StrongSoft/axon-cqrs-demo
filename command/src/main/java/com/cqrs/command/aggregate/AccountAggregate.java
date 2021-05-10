package com.cqrs.command.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.cqrs.command.commands.AccountCreationCommand;
import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.events.AccountCreationEvent;
import com.cqrs.events.DepositMoneyEvent;
import com.cqrs.events.WithdrawMoneyEvent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@RequiredArgsConstructor
@Aggregate
@Slf4j
@EqualsAndHashCode
@Entity(name = "account")
@Table(name = "account")
public class AccountAggregate {
  @AggregateIdentifier
  @Id
  @Column(name = "account_id")
  private String accountID;
  @ManyToOne
  @JoinColumn(name = "holder_id", foreignKey = @ForeignKey(name = "FK_HOLDER"))
  private HolderAggregate holder;
  private Long balance;

  public void registerHolder(HolderAggregate holder){
    if(this.holder != null){
      this.holder.unRegisterAccount(this);
    }
    this.holder = holder;
    this.holder.registerAccount(this);
  }

  @CommandHandler
  public AccountAggregate(AccountCreationCommand command){
    log.debug("handing{}", command);
    accountID = command.getAccountID();
    HolderAggregate holder = command.getHolder();
    registerHolder(holder);
    balance = 0L;
    apply(new AccountCreationEvent(holder.getHolderID(), command.getAccountID()));
  }

  @CommandHandler
  protected void depositMoney(DepositMoneyCommand command){
    log.debug("handling {}", command);
    if(command.getAmount() <= 0) {
      throw new IllegalStateException("amount >= 0");
    }
    balance += command.getAmount();
    apply(new DepositMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
  }

  @CommandHandler
  protected void withdrawMoney(WithdrawMoneyCommand command){
    log.debug("handling {}", command);
    if(balance - command.getAmount() < 0) {
      throw new IllegalStateException("잔고가 부족합니다.");
    } else if(command.getAmount() <= 0 ) {
      throw new IllegalStateException("amount >= 0");
    }
    balance -= command.getAmount();
    log.debug("balance {}", balance);
    apply(new WithdrawMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
  }

  /*@AggregateIdentifier
  private String accountID;
  private String holderID;
  private Long balance;

  @CommandHandler
  public AccountAggregate(AccountCreationCommand command) {
    log.debug("handling {}", command);
    apply(new AccountCreationEvent(command.getHolderID(),command.getAccountID()));
  }
  @EventSourcingHandler
  protected void createAccount(AccountCreationEvent event){
    log.debug("applying {}", event);
    accountID = event.getAccountID();
    holderID = event.getHolderID();
    balance = 0L;
  }
  @CommandHandler
  protected void depositMoney(DepositMoneyCommand command){
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
    log.debug("balance {}", balance);
  }
  @CommandHandler
  protected void withdrawMoney(WithdrawMoneyCommand command){
    log.debug("handling {}", command);
    if(balance - command.getAmount() < 0) {
      throw new IllegalStateException("잔고가 부족합니다.");
    } else if(command.getAmount() <= 0 ) {
      throw new IllegalStateException("amount >= 0");
    }
    apply(new WithdrawMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
  }
  @EventSourcingHandler
  protected void withdrawMoney(WithdrawMoneyEvent event){
    log.debug("applying {}", event);
    balance -= event.getAmount();
    log.debug("balance {}", balance);
  }*/
}
