package ASTNodes;

public class IfStatement implements ASTNode {
    private final ASTNode condition;
    private final CompoundStatement body;
    private final CompoundStatement elseBody;

    public IfStatement(ASTNode condition, CompoundStatement body, CompoundStatement elseBody) {
        this.body = body;
        this.condition = condition;
        this.elseBody = elseBody;
    }

    public CompoundStatement getBody() { return body; }

    public CompoundStatement getElseBody() { return elseBody;}

    public ASTNode getCondition() { return condition; }
}
