package Visitors;

import ASTNodes.*;
import Exceptions.InterpretException;
import Exceptions.RedeclaredSymbolException;
import Exceptions.SymbolException;
import Exceptions.SymbolNotTypeException;
import LanguageTypes.LanguageType;
import Symbols.*;

public class SymbolTableVisitor extends NodeVisitor {
    private final ScopedSymbolTable symbolTable = new ScopedSymbolTable();

    @Override
    public void eval() throws InterpretException, SymbolException {
        symbolTable.enterScope();
        super.eval();
        symbolTable.exitScope();
    }

    public SymbolTableVisitor() {
        super();
        declareBuiltins();
    }

    public SymbolTableVisitor(ASTNode tree) {
        super(tree);
        declareBuiltins();
    }

    private void declareBuiltins() {
        try {
            symbolTable.declare(new TypeSymbol("int"));
            symbolTable.declare(new TypeSymbol("float"));
        } catch (RedeclaredSymbolException e) {
            // Should not happen under any circumstances
            // If this happens, the program should panic immediately
            throw new RuntimeException("[SymbolTableVisitor.declareBuiltins] declaration called before the declaration of builtins");
        }
    }

    @Override
    public LanguageType visit(BinaryOperator binop)
            throws InterpretException, SymbolException {
        visit(binop.getLeft());
        visit(binop.getRight());
        return null;
    }

    @Override
    public LanguageType visit(IntegerConstant num)
        { return null; }

    @Override
    public LanguageType visit(FloatConstant num)
        { return null; }

    @Override
    public LanguageType visit(UnaryOperator unop)
            throws InterpretException, SymbolException {
        visit(unop.getNode());
        return null;
    }

    @Override
    public void visit(CompoundStatement comstat)
            throws InterpretException, SymbolException {
        for (ASTNode statement: comstat.getStatements())
            visit(statement);
    }

    @Override
    public void visit(VariableAssignment varas)
            throws InterpretException, SymbolException {
        // System.out.println("*** variable assigned " + varas.getName());
        symbolTable.define(varas.getName());
        visit(varas.getValue());
    }

    @Override
    public LanguageType visit(VariableLookup varlo) throws SymbolException {
        String name = varlo.getName();
        // System.out.println("+++ variable looked up " + name);
        VariableSymbol symbol = (VariableSymbol) symbolTable.lookup(name);
        if (symbol.getState() == VariableSymbol.State.DECLARED)
            throw new SymbolException("ERROR: Symbol '" + name + "' was not initialized");
        return null;
    }

    @Override
    public void visit(VariableDeclaration vardec)
            throws SymbolException {
        // System.out.println("--- variable declared " + vardec.getName());
        Symbol typeSymbol = symbolTable.lookup(vardec.getType().getValue());
        try {
            TypeSymbol type = (TypeSymbol) typeSymbol;
            symbolTable.declare(new VariableSymbol(vardec.getName(), type));
        } catch (ClassCastException e) {
            invalidTypeException(vardec.getName(), typeSymbol.getName());
        }
    }

    @Override
    public void visit(IfStatement ifstat) throws InterpretException, SymbolException {
        visit(ifstat.getCondition());
        symbolTable.enterScope();
        visit(ifstat.getBody());
        symbolTable.exitScope();
        if (ifstat.getElseBody() != null) {
            symbolTable.enterScope();
            visit(ifstat.getElseBody());
            symbolTable.exitScope();
        }
    }

    @Override
    public boolean visit(BooleanEqualsOperator booleq) throws InterpretException, SymbolException {
        visit(booleq.getLeft());
        visit(booleq.getRight());
        return false;
    }

    private void invalidTypeException(String varName, String typeName) throws SymbolNotTypeException {
        throw new SymbolNotTypeException("Can't declare `" + varName + "'. `" + typeName + "' is not a type ");
    }
}
