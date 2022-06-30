
public abstract class NodeVisitor {

    private ASTNode tree;

    public NodeVisitor(ASTNode tree) { this.tree = tree; }
    public NodeVisitor() { }

    public ASTNode getTree() { return tree; }
    public void setTree(ASTNode tree) { this.tree = tree; }

    public void eval() {
        visit(tree);
    }

    public Object visit(ASTNode node) throws NullPointerException, ClassCastException {
        if (node == null)
            throw new NullPointerException("`node` is null");
        else if (node instanceof BinaryOperator)
            return visit((BinaryOperator) node);
        else if (node instanceof IntegerConstant)
            return visit((IntegerConstant) node);
        else if (node instanceof FloatConstant)
            return visit((FloatConstant) node);
        else if (node instanceof UnaryOperator)
            return visit((UnaryOperator) node);
        else if (node instanceof CompoundStatement) {
            visit((CompoundStatement) node);
            return null;
        } else if (node instanceof NoOp) {
            visit((NoOp) node);
            return null;
        } else if (node instanceof VariableAssignment) {
            visit((VariableAssignment) node);
            return null;
        } else if (node instanceof VariableLookup) {
            return visit((VariableLookup) node);
        } else if (node instanceof VariableDeclaration) {
            visit((VariableDeclaration) node);
            return null;
        } else
            throw new ClassCastException("No valid cast for `node`");
    }

    public abstract LanguageType visit(BinaryOperator binop)
            throws NullPointerException, ClassCastException;

    public abstract LanguageType visit(IntegerConstant intconst);

    public abstract LanguageType visit(FloatConstant floatconst);

    public abstract LanguageType visit(UnaryOperator unpo)
            throws NullPointerException, ClassCastException;

    public abstract void visit(CompoundStatement comstat)
            throws NullPointerException, ClassCastException;

    public void visit(NoOp op) { }

    public abstract void visit(VariableAssignment varas)
            throws NullPointerException, ClassCastException;

    public abstract LanguageType visit(VariableLookup varlo);

    public abstract void visit(VariableDeclaration vardec);
}
