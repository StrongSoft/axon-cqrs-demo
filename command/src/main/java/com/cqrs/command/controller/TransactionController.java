package com.cqrs.command.controller;

import com.cqrs.command.dto.AccountDTO;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.HolderDTO;
import com.cqrs.command.dto.WithdrawalDTO;
import com.cqrs.command.service.TransactionService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping("/holder")
  public CompletableFuture<String> createHOlder(@RequestBody HolderDTO holderDTO){
    return transactionService.createHolder(holderDTO);
  }

  @PostMapping("/account")
  public CompletableFuture<String> createAccount(@RequestBody AccountDTO accountDTO){
    return transactionService.createAccount(accountDTO);
  }

  @PostMapping("/deposit")
  public CompletableFuture<String> deposit(@RequestBody DepositDTO transactionDTO){
    log.debug(transactionDTO.toString());
    return transactionService.depositMoney(transactionDTO);
  }

  @PostMapping("/withdrawal")
  public CompletableFuture<String> withdrawal(@RequestBody WithdrawalDTO transactionDTO){
    return transactionService.withdrawMoney(transactionDTO);
  }
}
