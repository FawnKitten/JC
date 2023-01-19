package ASTNodes;


import Main.Token;

public class VariableAssignment implements ASTNode {
    private Token token;
    private ASTNode value;
    private String name;

    public VariableAssignment(Token token, ASTNode value) {
        this.token = token;
        this.value = value;
        this.name = token.getValue();
    }

    public String getName() { return name; }
    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
    public ASTNode getValue() { return value; }
    public void setValue(ASTNode value) { this.value = value; }

}
