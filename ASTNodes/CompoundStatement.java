import java.util.ArrayList;

public class CompoundStatement implements ASTNode {
    private ArrayList<ASTNode> statements;

    public CompoundStatement(ArrayList<ASTNode> statements) {
        this.statements = statements;
    }

    public CompoundStatement() {
        this.statements = new ArrayList<ASTNode>();
    }

    public ArrayList<ASTNode> getStatements() { return statements; }
    public void setStatements(ArrayList<ASTNode> statements) { this.statements = statements; }

    public void addStatement(ASTNode statement) { statements.add(statement); }
    public void addMultipleStatements(ArrayList<ASTNode> multipleStatements) {
        statements.addAll(multipleStatements); }

    @Override
    public Object accept(NodeVisitor visitor) {
        visitor.visit(this);
        return null;
    }
}
