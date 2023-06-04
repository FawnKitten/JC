package Visitors;

import Exceptions.*;
import LanguageTypes.*;
import ASTNodes.*;
import Symbols.Symbol;

import java.util.HashMap;

public class InterpretVisitor extends NodeVisitor {
    final private HashMap<String, LanguageType> variables = new HashMap<>();

    public InterpretVisitor() { super(); }

    @Override
    public void eval() throws InterpretException, SymbolException {
        SymbolTableVisitor symtab = new SymbolTableVisitor(getTree());
        symtab.eval();
        visit(getTree());
        System.out.println("Variables:");
        System.out.println(variables);
        System.out.println();
    }

    @SuppressWarnings("CommentedOutCode")
    @Override
    public LanguageType visit(BinaryOperator binop)
            throws InterpretException, SymbolException {
        LanguageInteger one = new LanguageInteger(1);
        LanguageInteger zero = new LanguageInteger(0);
        LanguageType left = (LanguageType)visit(binop.getLeft());
        LanguageType right = (LanguageType)visit(binop.getRight());
        // symbol table should not let undeclared symbols to exist
        // this code is being kept anyway in case of bugs
        // if (left == null) {
        //     throw new NullPointerException(
        //             "Uninitialized variable "
        //             + ((VariableLookup)(binop.getLeft())).getName()
        //             + ".");
        //             // + "(had the value of"")");
        // } else if (right == null) {
        //     throw new NullPointerException(
        //             "Uninitialized variable "
        //             + ((VariableLookup)(binop.getRight())).getName()
        //             + ".");
        //             // + "(had the value of"")");
        // }
        int lval = ((LanguageInteger)left).getNumber();
        int rval = ((LanguageInteger)right).getNumber();
        switch (binop.getToken().getType()) {
            case PLUS: return left.plus(right);
            case DASH: return left.minus(right);
            case SLASH: return left.divide(right);
            case STAR: return left.times(right);
            case BOOL_EQUALS: return left.equals(right) ? one : zero;
            case BOOL_GREATER: return left.compareTo(right);
            case BOOL_LESSER: return right.compareTo(left);
            case BOOL_AND: return new LanguageInteger(((rval != 0) && (lval != 0)) ? 1 : 0);
            case BOOL_OR: return new LanguageInteger(((rval != 0) || (lval != 0)) ? 1 : 0);
        }
        throw new RuntimeException("[InterpretVisitor.visit(BinaryOperator)] type not handled");
    }


    @Override
    public LanguageType visit(IntegerConstant intconst) {
        return new LanguageInteger(intconst.getValue());
    }

    @Override
    public LanguageType visit(FloatConstant floatconst) {
        return new LanguageFloat(floatconst.getValue());
    }

    @Override
    public LanguageType visit(UnaryOperator unop)
            throws InterpretException, SymbolException {
        LanguageInteger zero = new LanguageInteger(0);
        LanguageType value = (LanguageType)visit(unop.getNode());
        switch (unop.getToken().getType()) {
           case PLUS: return zero.plus(value);
           case DASH: return zero.minus(value);
        }
        throw new RuntimeException("[InterpretVisitor.visit(UnaryOperator)] type not handled");
    }

    @Override
    public void visit(CompoundStatement comstat)
            throws InterpretException, SymbolException {
        for (ASTNode stat: comstat.getStatements())
            visit(stat);
    }

    @Override
    public void visit(VariableAssignment varas)
            throws InterpretException, SymbolException {
        LanguageType value = (LanguageType)visit(varas.getValue());
        String name = varas.getName();
        // System.out.println("*** " + name + " <- " + value.toString());
        // for debuting purposes uncomment
        variables.put(name, value);
    }

    @Override
    public LanguageType visit(VariableLookup varlo) {
        String name = varlo.getName();
        @SuppressWarnings("UnnecessaryLocalVariable") LanguageType value = variables.get(name);
        // System.out.println("*** " + name + " -> " + value.toString());
        // for debugging purposes uncomment
        return value;
    }

    @Override
    public void visit(VariableDeclaration vardec) {
        String name = vardec.getName();
        // System.out.println("*** " + name + " := " + type);
        // for debuting purposes uncomment
        variables.put(name, null);
    }

    @Override
    public void visit(IfStatement ifstat) throws InterpretException, SymbolException {
        if (visit(ifstat.getCondition()) instanceof LanguageInteger condition) {
            LanguageInteger zero = new LanguageInteger(0);
            if (!(condition.getNumber() == zero.getNumber())) {
                visit(ifstat.getBody());
            } else if (ifstat.getElseBody() != null) {
                visit(ifstat.getElseBody());
            }
        }
    }
}
