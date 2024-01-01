package ASTNodes;

import Main.Token;

public class FloatConstant implements ASTNode {
    private final Token token;
    private final Float value;

    public FloatConstant(Token token) {
        this.token = token;
        this.value = Float.parseFloat(token.getValue());
    }

    public Token getToken() { return token; }

    public Float getValue() { return value; }

}
