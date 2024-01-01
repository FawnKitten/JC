package Visitors;

import Exceptions.*;
import ASTNodes.*;
import Main.LanguageType;

import java.util.*;

public class InterpretVisitor extends NodeVisitor {
    final private HashMap<String, Object> variables = new HashMap<>();
    final private HashMap<String, ASTNode> functions = new HashMap<>();

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
    public Object visit(BinaryOperator binop)
            throws InterpretException, SymbolException {
        Object left = visit(binop.getLeft());
        Object right = visit(binop.getRight());
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

        switch (binop.getToken().getType()) {
            case PLUS: return LanguageType.add(left, right);
            case DASH: return LanguageType.subtract(left, right);
            case SLASH: return LanguageType.divide(left, right);
            case STAR: return LanguageType.multiply(left, right);
            case BOOL_EQUALS: return LanguageType.equals(left, right);
            case BOOL_GREATER: return LanguageType.greaterThan(left, right);
            case BOOL_LESSER: return LanguageType.lessThan(left, right);
            case BOOL_AND: return LanguageType.and(left, right);
            case BOOL_OR: return LanguageType.or(left, right);
        }
        throw new RuntimeException("[InterpretVisitor.visit(BinaryOperator)] type not handled");
    }


    @Override
    public Object visit(IntegerConstant intconst) {
        return intconst.getValue();
    }

    @Override
    public Object visit(FloatConstant floatconst) {
        return floatconst.getValue();
    }

    @Override
    public Object visit(UnaryOperator unop)
            throws InterpretException, SymbolException {
        Object value = visit(unop.getNode());
        switch (unop.getToken().getType()) {
            case PLUS: return LanguageType.add(0,  value);
            case DASH: return LanguageType.subtract(0, value);
            case BOOL_NOT: return LanguageType.not(value);
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
        Object value = visit(varas.getValue());
        String name = varas.getName();
        // System.out.println("*** " + name + " <- " + value.toString());
        // for debuting purposes uncomment
        variables.put(name, value);
    }

    @Override
    public Object visit(VariableLookup varlo) {
        String name = varlo.getName();
        @SuppressWarnings("UnnecessaryLocalVariable") Object value = variables.get(name);
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
        Object condition = visit(ifstat.getCondition());
        if (condition instanceof Integer) condition = !condition.equals(0);
        if ((Boolean) condition) {
            visit(ifstat.getBody());
        } else if (ifstat.getElseBody() != null) {
            visit(ifstat.getElseBody());
        }
    }

    @Override
    public void visit(WhileStatement whilestat) throws InterpretException, SymbolException {
        Object condition = visit(whilestat.getCondition());
        if (condition instanceof Integer) condition = !condition.equals(0);
        while ((Boolean) condition) {
            visit(whilestat.getBody());
            condition = visit(whilestat.getCondition());
            if (condition instanceof Integer) condition = !condition.equals(0);
        }
    }

    @Override
    public Object visit(FunctionCall funccall) throws InterpretException, SymbolException {
        // InterpretVisitor TODO: Make Print into its own funtction (un-hard code it)
        if (funccall.getName().getValue().equals("printf")) {
            String message = ((StringConstant) funccall.getArguments().get(0)).getValue();
            List<ASTNode> formatArgs = funccall.getArguments().subList(1, funccall.getArguments().size());
            List<Object> formatArgsValues = new ArrayList<>();
            for (ASTNode arg : formatArgs) {
                formatArgsValues.add(visit(arg));
            }
            System.out.println("[printf] " + message.formatted(formatArgsValues.toArray()));
        } else if (funccall.getName().getValue().equals("DEBUG")) {
            System.out.print("[debug]");
            for (ASTNode arg : funccall.getArguments()) {
                if (arg instanceof VariableLookup) {
                    String name = ((VariableLookup) arg).getName();
                    System.out.print(" " + name + "=" + variables.get(name).toString());
                }
            }
            System.out.println();
        }
        return null;
    }

    @Override
    public Object visit(StringConstant strconst) throws InterpretException, SymbolException {
        return strconst.getValue();
    }

}
