package ASTNodes;

import Main.Token;

public class ArrayDeclaration implements ASTNode {
    Token name;
    Token collectionType;
    int size;

    @Override
    public String toString() {
        return "ArrayDeclaration(" +
                "name=" + name +
                ", collectionType=" + collectionType +
                ", size=" + size +
                ')';
    }

    public Token getName() {
        return name;
    }

    public Token getCollectionType() {
        return collectionType;
    }

    public int getSize() {
        return size;
    }

    public ArrayDeclaration(Token name, Token collectionType, Token size) {
        this.name = name;
        this.collectionType = collectionType;
        this.size = Integer.parseInt(size.getValue());
    }
}
