package com.sisterhore.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

import com.sisterhore.crdt.Char;
import com.sisterhore.util.Serializer;
import com.sisterhore.version.Version;

public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;
	public OperationType operationType;
	public char characterUsed;
	public int index;
	public Char data;
	public Version version;

	public Operation(OperationType operationType, char characterUsed, int index, Version version) {
		this.operationType = operationType;
		this.characterUsed = characterUsed;
		this.index = index;
		this.version = version;
		this.data = null;
	}

	public void setData(Char data) { this.data = data; }

	public Char getData() { return this.data; }

  public Version getVersion() { return this.version; }

  public void setVersion(Version version) { this.version = version; }

  public char getCharacterUsed() { return this.characterUsed; }

  public OperationType getOperationType() { return operationType; }

  public int getIndex() { return this.index; }

	public String toString() {
		String output = String.format("operationType: %s\ncharacter: %c\nindex: %d\nsiteId: %s\nversion: %d\n",
				this.operationType.toString(), this.characterUsed, this.index, this.version.getSiteId(),
				this.version.getCounter());

		return output;
	}

  public static Operation getOperation(String message) {
    byte[] bytes = Base64.getDecoder().decode(message);
    Operation operation = null;
    try {
      operation = (Operation) Serializer.deserialize(bytes);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    return operation;
  }

}