package com.sisterhore.controller;

import com.sisterhore.crdt.Char;
import com.sisterhore.version.Version;

enum OperationType {INSERT, DELETE}

public class Operation {
    public OperationType operationType;
    public Char characterUsed;
    public Version version;
}