package com.cqrs.command.repository;

import com.cqrs.command.aggregate.HolderAggregate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolderRepository extends JpaRepository<HolderAggregate, String> {
  Optional<HolderAggregate> findHolderAggregateByHolderID(String id);
}
