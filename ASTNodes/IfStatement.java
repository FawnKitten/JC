package ASTNodes;

public class IfStatement implements ASTNode {
    private ASTNode condition;
    private CompoundStatement body;

    public IfStatement(ASTNode condition, CompoundStatement body) {
        this.body = body;
        this.condition = condition;
    }

    public CompoundStatement getBody() { return body; }

    public ASTNode getCondition() { return condition; }
}
