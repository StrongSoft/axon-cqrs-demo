package com.cqrs.command.service;

import com.cqrs.command.commands.AccountCreationCommand;
import com.cqrs.command.commands.DepositMoneyCommand;
import com.cqrs.command.commands.HolderCreationCommand;
import com.cqrs.command.commands.WithdrawMoneyCommand;
import com.cqrs.command.dto.AccountDTO;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.HolderDTO;
import com.cqrs.command.dto.WithdrawalDTO;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

  private final CommandGateway commandGateway;
  //private final HolderRepository holders;

  @Override
  public CompletableFuture<String> createHolder(HolderDTO holderDTO) {
    return commandGateway.send(new HolderCreationCommand(UUID.randomUUID().toString()
    ,holderDTO.getHolderName()
    ,holderDTO.getTel()
    ,holderDTO.getAddress()));
  }

  @Override
  public CompletableFuture<String> createAccount(AccountDTO accountDTO) {
    log.debug(accountDTO.getHolderID());
    return commandGateway.send(new AccountCreationCommand(UUID.randomUUID().toString(), accountDTO.getHolderID()));
    /*HolderAggregate holder = holders.findHolderAggregateByHolderID(accountDTO.getHolderID())
        .orElseThrow(() -> new IllegalAccessError("계정 ID가 올바르지 않습니다."));
    return commandGateway.send(new AccountCreationCommand(UUID.randomUUID().toString(), holder));*/
  }

  @Override
  public CompletableFuture<String> depositMoney(DepositDTO transactionDTO) {
    return commandGateway.send(new DepositMoneyCommand(transactionDTO.getAccountID()
    ,transactionDTO.getHolderID()
    ,transactionDTO.getAmount()));
  }

  @Override
  public CompletableFuture<String> withdrawMoney(WithdrawalDTO transactionDTO) {
    return commandGateway.send(new WithdrawMoneyCommand(transactionDTO.getAccountID()
        ,transactionDTO.getHolderID()
        ,transactionDTO.getAmount()));
  }
}
