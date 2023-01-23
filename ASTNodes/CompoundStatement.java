package ASTNodes;

import java.util.ArrayList;

public class CompoundStatement implements ASTNode {
    private final ArrayList<ASTNode> statements;

    public CompoundStatement(ArrayList<ASTNode> statements) {
        this.statements = statements;
    }

    public CompoundStatement() {
        this.statements = new ArrayList<>();
    }

    public ArrayList<ASTNode> getStatements() { return statements; }

    public void addStatement(ASTNode statement) { statements.add(statement); }
    public void addMultipleStatements(ArrayList<ASTNode> multipleStatements) {
        statements.addAll(multipleStatements); }
}
