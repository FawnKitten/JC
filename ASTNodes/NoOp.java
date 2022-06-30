
public class NoOp implements ASTNode {
    public Object accept(NodeVisitor visitor) {
        visitor.visit(this);
        return null;
    }

}
