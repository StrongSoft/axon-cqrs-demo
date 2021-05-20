package com.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.serialization.Revision;

@AllArgsConstructor
@ToString
@Getter
@Revision("1.0")
public class HolderCreationEvent {
  private final String holderID;
  private final String holderName;
  private final String tel;
  private final String address;
  private final String company;
}
