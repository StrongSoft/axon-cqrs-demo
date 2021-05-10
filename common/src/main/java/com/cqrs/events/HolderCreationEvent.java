package com.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class HolderCreationEvent {
  private final String holderID;
  private final String holderName;
  private final String tel;
  private final String address;
}
