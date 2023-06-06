package ASTNodes;

public class WhileStatement implements ASTNode {
    private final ASTNode condition;
    private final ASTNode body;

    public WhileStatement(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getBody() {
        return body;
    }
}
