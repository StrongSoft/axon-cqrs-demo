package com.cqrs.query.repository;

import com.cqrs.query.entity.HolderAccountSummary;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<HolderAccountSummary, Long> {
  Optional<HolderAccountSummary> findByHolderId(String holderId);
}
