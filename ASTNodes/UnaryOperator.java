
public class UnaryOperator implements ASTNode {
    private Token token;
    private ASTNode node;

    public UnaryOperator(Token token, ASTNode node) {
        this.token = token;
        this.node = node;
    }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
    public ASTNode getNode() { return node; }
    public void setNode(ASTNode node) { this.node = node; }

}
