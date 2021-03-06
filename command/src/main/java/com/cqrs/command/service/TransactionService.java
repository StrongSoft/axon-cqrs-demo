package com.cqrs.command.service;

import com.cqrs.command.dto.AccountDTO;
import com.cqrs.command.dto.DepositDTO;
import com.cqrs.command.dto.HolderDTO;
import com.cqrs.command.dto.TransferDTO;
import com.cqrs.command.dto.WithdrawalDTO;
import java.util.concurrent.CompletableFuture;

public interface TransactionService {
  CompletableFuture<String> createHolder(HolderDTO holderDTO);
  CompletableFuture<String> createAccount(AccountDTO accountDTO);
  CompletableFuture<String> depositMoney(DepositDTO depositDTO);
  CompletableFuture<String> withdrawMoney(WithdrawalDTO withdrawalDTO);
  String transferMoney(TransferDTO transferDTO);
}
