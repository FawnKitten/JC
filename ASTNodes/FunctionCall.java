package ASTNodes;

import Main.Token;

import java.util.ArrayList;

public class FunctionCall implements ASTNode {

    private final Token name;
    private final ArrayList<ASTNode> arguments;

    public FunctionCall(Token name, ArrayList<ASTNode> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Token getName() {
        return name;
    }

    public ArrayList<ASTNode> getArguments() {
        return arguments;
    }
}
