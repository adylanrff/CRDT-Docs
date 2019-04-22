package com.sisterhore.controller;

import com.sisterhore.crdt.Char;
import com.sisterhore.version.Version;

enum OperationType {
  DELETE, INSERT
}

public class Operation {
  public OperationType operationType;
  public char characterUsed;
  public Version version;
  public int index;

  public Operation(OperationType operationType, char characterUsed, int index) {
    this.operationType = operationType;
    this.characterUsed = characterUsed;
    this.index = index;
  }

  public Version getVersion() { return this.version; }

  public void setVersion(Version version) { this.version = version; }

  public char getCharacterUsed() { return this.characterUsed; }

  public OperationType getOperationType() { return operationType; }

  public int getIndex() { return this.index; }

}