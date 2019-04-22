package com.sisterhore.controller;

import com.sisterhore.crdt.Char;
import com.sisterhore.version.Version;

enum OperationType {
  DELETE, INSERT
}

public class Operation {
  private OperationType operationType;
  private Char characterUsed;
  private Version version;
  private Integer index;

  public Operation() {

  }
}