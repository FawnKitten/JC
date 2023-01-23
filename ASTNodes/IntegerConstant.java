package ASTNodes;


import Main.Token;

public class IntegerConstant implements ASTNode {
    private final Token token;
    private final int value;

    public IntegerConstant(Token token) {
        this.token = token;
        this.value = Integer.parseInt(token.getValue());
    }

    public Token getToken() { return token; }

    public int getValue() { return value; }

}
