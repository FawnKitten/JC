package Visitors;

import ASTNodes.*;
import Exceptions.InterpretException;
import Exceptions.SymbolException;

public class PrintVisitor extends NodeVisitor {
    private int indentation;
    private ASTNode tree;

    public PrintVisitor(ASTNode tree) { super(tree); }

    public PrintVisitor() { super(); }

    @Override
    public Object visit(BinaryOperator binop)
        throws InterpretException, SymbolException {
        print("BinaryOperation<" + binop.getToken().getValue() + ">(");
        indentation++;
        visit(binop.getLeft());
        visit(binop.getRight());
        indentation--;
        print(")");

        return null;
    }

    @Override
    public Object visit(IntegerConstant intconst) {
        print("IntegerConstant(" + intconst.getToken().getValue() + ")");
        return null;
    }

    @Override
    public Object visit(FloatConstant floatconst) {
        print("FloatConstant(" + floatconst.getToken().getValue() + ")");
        return null;
    }

    @Override
    public Object visit(UnaryOperator unop)
        throws InterpretException, SymbolException {
        print("UnaryOperator<" + unop.getToken().getValue() + ">(");
        indentation++;
        visit(unop.getNode());
        indentation--;
        print(")");
        return null;
    }

    @Override
    public void visit(CompoundStatement comstat)
        throws InterpretException, SymbolException {
        print("CompoundStatement(");
        indentation++;
        for (ASTNode statement: comstat.getStatements()) {
            visit(statement);
        }
        indentation--;
        print(")");
    }

    @Override
    public void visit(NoOp op) {
        print("NoOp()");
    }

    @Override
    public void visit(VariableAssignment varas)
            throws InterpretException, SymbolException {
        print("VariableAssignment<" + varas.getToken().getValue() + ">(");
        indentation++;
        visit(varas.getValue());
        indentation--;
        print(")");
    }

    @Override
    public Object visit(VariableLookup varlo) {
        print("VariableLookup<" + varlo.getName() + ">()");
        return null;
    }

   @Override
   public void visit(VariableDeclaration vardec) {
       print("VariableDeclaration<" + vardec.getName()
               + ", " + vardec.getType().getValue() + ">()");
   }

    @Override
    public void visit(ArrayDeclaration arrdec) throws SymbolException {
        print("ArrayDeclaration<" + arrdec.getName().getValue()
                + ", " + arrdec.getCollectionType().getValue()
                + ", " + arrdec.getSize() + ">()");
    }

    @Override
    public void visit(ArrayAssignment arras) throws SymbolException, InterpretException {
        print("ArrayAssignment<" + arras.getName().getValue() + ">(");
        indentation++;
        visit(arras.getIndex());
        visit(arras.getValue());
        indentation--;
        print(")");
    }

    @Override
    public Object visit(ArrayLookup arrlo) throws SymbolException, InterpretException {
        print("ArrayLookup<" + arrlo.getName().getValue() + ">(");
        indentation++;
        visit(arrlo.getIndex());
        indentation--;
        print(")");
        return null;
    }

    @Override
   public void visit(IfStatement ifstat) throws InterpretException, SymbolException {
        print("IfStatement( Condition:");
        indentation++;
        visit(ifstat.getCondition());
        print(", Body:");
        visit(ifstat.getBody());
        if (ifstat.getElseBody() != null) {
            print(", Else:");
            visit(ifstat.getElseBody());
        }
       indentation--;
        print(")");
   }

    @Override
    public void visit(WhileStatement whileStat) throws InterpretException, SymbolException {
        print("WhileStatement( Condition:");
        indentation++;
        visit(whileStat.getCondition());
        print(", Body:");
        visit(whileStat.getBody());
        indentation--;
        print(")");
    }

    @Override
    public Object visit(FunctionCall funccall) throws InterpretException, SymbolException {
        print("FunctionCall<" + funccall.getName().getValue() + ">(");
        indentation++;
        for (ASTNode arg : funccall.getArguments())
            visit(arg);
        indentation--;
        print(")");
        return null;
    }

    @Override
    public Object visit(StringConstant strconst) throws InterpretException, SymbolException {
        print("StringConstant(" + strconst.getValue() + ")");
        return null;
    }


    private String pad(int num) {
        return "-".repeat(Math.max(0, num));
    }

    private void print(String message) {
        System.out.println(pad(indentation) + message);
    }
}
