package ASTNodes;

import Main.Token;

public class ArrayLookup implements ASTNode {
    private final Token name;
    private final ASTNode index;

    public ArrayLookup(Token name, ASTNode index) {
        this.name = name;
        this.index = index;
    }

    public Token getName() { return name; }

    public ASTNode getIndex() { return index; }
}
