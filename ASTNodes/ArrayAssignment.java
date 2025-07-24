package ASTNodes;

import Main.Token;

public class ArrayAssignment implements ASTNode {
    Token name;
    ASTNode index;
    ASTNode value;

    public ArrayAssignment(Token name, ASTNode index, ASTNode value) {
        this.name = name;
        this.index = index;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ArrayAssignment(" +
                "name=" + name +
                ", index=" + index +
                ", value=" + value +
                ')';
    }

    public Token getName() {
        return name;
    }

    public ASTNode getIndex() {
        return index;
    }

    public ASTNode getValue() {
        return value;
    }
}
