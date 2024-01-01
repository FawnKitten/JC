package ASTNodes;

import Main.Token;

public class StringConstant implements ASTNode {
    private final Token token;
    private final String value;

    public StringConstant(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
}
