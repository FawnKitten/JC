
public class VariableDeclaration implements ASTNode {
    private Token type;
    private Token token;

    public VariableDeclaration(Token token, Token type) {
        this.token = token;
        this.type = type;
    }

    public String getName() { return token.getValue(); }

    public Token getType() { return type; }
    public void setType(Token type) { this.type = type; }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

}
