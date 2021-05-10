package com.cqrs.command.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.cqrs.command.commands.HolderCreationCommand;
import com.cqrs.events.HolderCreationEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@RequiredArgsConstructor
@Aggregate
@Slf4j
@Entity(name = "holder")
@Table(name = "holder")
public class HolderAggregate {
  @AggregateIdentifier
  @Id
  @Column(name = "holder_id")
  @Getter
  private String holderID;
  @Column(name = "holder_name")
  private String holderName;
  private String tel;
  private String address;

  @OneToMany(mappedBy = "holder", orphanRemoval = true, fetch = FetchType.EAGER)
  private final List<AccountAggregate> accounts = new ArrayList<>();

  public void registerAccount(AccountAggregate account){
    if(!accounts.contains(account)){
      accounts.add(account);
    }
  }

  public void unRegisterAccount(AccountAggregate account){
    accounts.remove(account);
  }

  @CommandHandler
  public HolderAggregate(HolderCreationCommand command){
    log.debug("handling {}",command);

    holderID = command.getHolderID();
    holderName = command.getHolderName();
    tel = command.getTel();
    address = command.getAddress();

    apply(new HolderCreationEvent(command.getHolderID(), command.getHolderName(), command.getTel(), command.getAddress()));
  }

  /*@AggregateIdentifier
  private String holderID;
  private String holderName;
  private String tel;
  private String address;

  @CommandHandler
  public HolderAggregate(HolderCreationCommand command){
    log.debug("handling {}", command);
    apply(new HolderCreationEvent(command.getHolderID(), command.getHolderName(), command.getTel(), command.getAddress()));
  }

  @EventSourcingHandler
  protected void createHolder(HolderCreationEvent event){
    log.debug("applying {}", event);
    holderID = event.getHolderID();
    holderName = event.getHolderName();
    tel = event.getTel();
    address = event.getAddress();
  }*/
}
