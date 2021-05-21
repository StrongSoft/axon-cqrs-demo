package com.cqrs.jeju.service;

import com.cqrs.jeju.command.AccountCreationCommand;
import com.cqrs.jeju.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final CommandGateway commandGateway;

  @Override
  public String createAccount(AccountDTO accountDTO) {
    log.info("accountDTO {}",accountDTO.toString());
    return commandGateway.sendAndWait(new AccountCreationCommand(accountDTO.getAccountID(),
        accountDTO.getBalance()));
  }
}
