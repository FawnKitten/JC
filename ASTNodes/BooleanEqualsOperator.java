package ASTNodes;

import Exceptions.InterpretException;
import Exceptions.SymbolException;

public class BooleanEqualsOperator implements ASTNode {
    private final ASTNode left;
    private final ASTNode right;

    public BooleanEqualsOperator(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }
}
