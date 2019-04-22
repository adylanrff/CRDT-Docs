package com.sisterhore.controller;

public class Operation {
  public OperationType operationType;
  public char characterUsed;
  public int index;

  public Operation(OperationType operationType, char characterUsed, int index){
      this.operationType = operationType;
      this.characterUsed = characterUsed;
      this.index = index;
  }
}