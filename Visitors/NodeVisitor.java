package Visitors;

import ASTNodes.*;
import Exceptions.InterpretException;
import Exceptions.InvalidNodeTypeException;
import Exceptions.SymbolException;

public abstract class NodeVisitor {

    private ASTNode tree;

    public NodeVisitor(ASTNode tree) { this.tree = tree; }
    public NodeVisitor() { }

    public ASTNode getTree() { return tree; }
    public void setTree(ASTNode tree) { this.tree = tree; }

    public void eval()
        throws InterpretException, SymbolException {
        visit(getTree());
    }

    public Object visit(ASTNode node)
        throws InterpretException, SymbolException {
        if (node instanceof BinaryOperator)
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
        } else if (node instanceof IfStatement) {
            visit((IfStatement) node);
            return null;
        } else if (node instanceof WhileStatement) {
            visit((WhileStatement) node);
            return null;
        } else if (node instanceof FunctionCall) {
            visit((FunctionCall) node);
            return null;
        } else if (node instanceof StringConstant) {
            return visit((StringConstant) node);
        } else
            throw new InvalidNodeTypeException("No valid cast for `" + node.getClass().getName() + "'");
    }

    public abstract Object visit(BinaryOperator binop)
            throws InterpretException, SymbolException;

    public abstract Object visit(IntegerConstant intconst);

    public abstract Object visit(FloatConstant floatconst);

    public abstract Object visit(UnaryOperator unpo)
            throws InterpretException, SymbolException;

    public abstract void visit(CompoundStatement comstat)
            throws InterpretException, SymbolException;

    public void visit(@SuppressWarnings("unused") NoOp op) { }

    public abstract void visit(VariableAssignment varas)
            throws InterpretException, SymbolException;

    public abstract Object visit(VariableLookup varlo)
            throws SymbolException;

    public abstract void visit(VariableDeclaration vardec)
            throws SymbolException;

    public abstract void visit(IfStatement ifstat)
            throws InterpretException, SymbolException;

    public abstract void visit(WhileStatement whilestat)
            throws InterpretException, SymbolException;

    public abstract Object visit(FunctionCall funccall)
            throws InterpretException, SymbolException;

    public abstract Object visit(StringConstant strconst)
            throws InterpretException, SymbolException;
}
