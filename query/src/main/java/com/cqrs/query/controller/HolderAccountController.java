package com.cqrs.query.controller;

import com.cqrs.query.entity.HolderAccountSummary;
import com.cqrs.query.loan.LoanLimitResult;
import com.cqrs.query.service.QueryService;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HolderAccountController {
  private final QueryService queryService;

  @PostMapping("/reset")
  public void reset(){
    queryService.reset();
  }

  @GetMapping("/account/info/{id}")
  public ResponseEntity<HolderAccountSummary> getAccountInfo(@PathVariable(value = "id") @NonNull @NotBlank String holderId){
    return ResponseEntity.ok().body(queryService.getAccountInfo(holderId));
  }

  @GetMapping("account/info/subscription/{id}")
  public ResponseEntity<Flux<HolderAccountSummary>> getAccountInfoSubscription(@PathVariable(value = "id") @NonNull @NotBlank String holderId){
    return ResponseEntity.ok()
        .body(queryService.getAccountInfoSubscription(holderId));
  }

  @GetMapping("account/info/scatter/gather/{id}")
  public ResponseEntity<List<LoanLimitResult>> getAccountInfoScatterGather(@PathVariable(value = "id") @NonNull @NotBlank String holderId){
    log.info("id {}",holderId);
    return ResponseEntity.ok().body(queryService.getAccountInfoScatterGather(holderId));
  }
}
