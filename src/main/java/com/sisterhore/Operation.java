enum OperationType {INSERT, DELETE}

public class Operation {
    private OperationType operationType;
    private Character characaterUsed;
    private Integer version;
    private Integer index;
}