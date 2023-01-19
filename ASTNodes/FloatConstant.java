package ASTNodes;

import Main.*;
import Visitors.NodeVisitor;

public class FloatConstant implements ASTNode {
    private Token token;
    private float value;

    public FloatConstant(Token token) {
        this.token = token;
        this.value = Float.parseFloat(token.getValue());
    }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    public Object accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
