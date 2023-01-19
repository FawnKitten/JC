package ASTNodes;


import Main.Token;

public class VariableLookup implements ASTNode {
    private Token token;

    public VariableLookup(Token token) {
        this.token = token;
    }

    public String getName() { return token.getValue(); }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

}
