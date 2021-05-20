package com.cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@ToString
@Getter
public class HolderCreationCommand {
  @TargetAggregateIdentifier
  private final String holderID;
  private final String holderName;
  private final String tel;
  private final String address;
  private final String company;
}
