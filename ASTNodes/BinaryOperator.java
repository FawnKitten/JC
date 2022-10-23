
public class BinaryOperator implements ASTNode {
    private Token token;
    private ASTNode left;
    private ASTNode right;

    public BinaryOperator(Token token, ASTNode left, ASTNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
    public ASTNode getLeft() { return left; }
    public void setLeft(ASTNode node) { this.left = node; }
    public ASTNode getRight() { return right; }
    public void setRight(ASTNode node) { this.right = node; }

}
