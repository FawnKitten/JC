
public class IntegerConstant implements ASTNode {
    private Token token;
    private int value;

    public IntegerConstant(Token token) {
        this.token = token;
        this.value = Integer.parseInt(token.getValue());
    }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public Object accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
