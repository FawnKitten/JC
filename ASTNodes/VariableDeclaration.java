
public class VariableDeclaration implements ASTNode {
    private Token name;
    private Token type;

    public VariableDeclaration(Token name, Token type) {
        this.name = name;
        this.type = type;
    }

    public Token getName() { return name; }
    public void setName(Token name) { this.name = name; }

    public Token getType() { return type; }
    public void setType(Token type) { this.type = type; }

    public Object accept(NodeVisitor visitor) {
        visitor.visit(this);
        return null;
    }
}
