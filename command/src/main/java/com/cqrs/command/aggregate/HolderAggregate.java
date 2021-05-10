import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.cqrs.command.commands.HolderCreationCommand;
import com.cqrs.events.HolderCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@RequiredArgsConstructor
@Aggregate
@Slf4j
public class HolderAggregate {
  @AggregateIdentifier
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
  protected void createAccount(HolderCreationEvent event){
    log.debug("applying {}", event);
    holderID = event.getHolderID();
    holderName = event.getHolderName();
    tel = event.getTel();
    address = event.getAddress();
  }
}