
public class VariableLookup implements ASTNode {
    private Token name;

    public VariableLookup(Token name) {
        this.name = name;
    }

    public Token getName() { return name; }
    public void setName(Token name) { this.name = name; }

    public Object accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
