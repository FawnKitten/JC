package ASTNodes;

import Main.*;

public class BinaryOperator implements ASTNode {
    private final Token token;
    private final ASTNode left;
    private final ASTNode right;

    public BinaryOperator(Token token, ASTNode left, ASTNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    public Token getToken() { return token; }

    public ASTNode getLeft() { return left; }

    public ASTNode getRight() { return right; }

}
