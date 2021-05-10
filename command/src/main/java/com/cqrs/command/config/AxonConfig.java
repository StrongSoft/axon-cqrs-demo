package com.cqrs.command.config;

import com.cqrs.command.aggregate.AccountAggregate;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;

//@Configuration
//@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {
  /*@Bean
  SimpleCommandBus commandBus(TransactionManager transactionManager){
    return SimpleCommandBus.builder().transactionManager(transactionManager).build();
  }*/
  @Bean
  public AggregateFactory<AccountAggregate> aggregateFactory(){
    return new GenericAggregateFactory<>(AccountAggregate.class);
  }

  @Bean
  public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager){
    return AggregateSnapshotter.builder()
        .eventStore(eventStore)
        .aggregateFactories(aggregateFactory())
        .transactionManager(transactionManager)
        .build();
  }

  @Bean
  public SnapshotTriggerDefinition snapshotTriggerDefinition(EventStore eventStore, TransactionManager transactionManager){
    final int SNAPSHOT_TRHRESHOLD = 5;
    return new EventCountSnapshotTriggerDefinition(snapshotter(eventStore, transactionManager), SNAPSHOT_TRHRESHOLD);
  }

  @Bean
  public Cache cache(){
    return new WeakReferenceCache();
  }

  @Bean
  public Repository<AccountAggregate> accountAggregateRepository(EventStore eventStore, SnapshotTriggerDefinition snapshotTriggerDefinition){
    return EventSourcingRepository.builder(AccountAggregate.class)
        .eventStore(eventStore)
        .snapshotTriggerDefinition(snapshotTriggerDefinition)
        .cache(cache())
        .build();
  }

}
