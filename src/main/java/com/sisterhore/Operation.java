import com.sisterhore.crdt.Char;

enum OperationType {INSERT, DELETE}

public class Operation {
    private OperationType operationType;
    private Char characterUsed;
    private Integer version;
    private Integer index;
}