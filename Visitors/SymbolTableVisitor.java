package Visitors;

import ASTNodes.*;
import Exceptions.*;
import LanguageTypes.LanguageType;
import Symbols.*;

import java.util.List;

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
            declareBuiltinTypes();
            declareBuiltinFuncitons();
        } catch (SymbolException e) {
            // Should not happen under any circumstances
            // If this happens, the program should panic immediately
            throw new RuntimeException("[SymbolTableVisitor.declareBuiltins] declaration called before the declaration of builtins");
        }
    }

    private void declareBuiltinTypes() throws RedeclaredSymbolException {
        symbolTable.declare(new TypeSymbol("int"));
        symbolTable.declare(new TypeSymbol("float"));
        symbolTable.declare(new TypeSymbol("String")); // delete when introduce char arrays
    }

    private void declareBuiltinFuncitons() throws RedeclaredSymbolException, UndefinedSymbolException {
        TypeSymbol stringType = (TypeSymbol) symbolTable.lookup("String");
        symbolTable.declare(new FunctionSymbol("printf", List.of(stringType)));
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
    public void visit(WhileStatement whileStat) throws InterpretException, SymbolException {
        visit(whileStat.getCondition());
        symbolTable.enterScope();
        visit(whileStat.getBody());
        symbolTable.exitScope();
    }

    @Override
    public LanguageType visit(FunctionCall funccall) throws InterpretException, SymbolException {
        try {
            FunctionSymbol function = (FunctionSymbol) symbolTable.lookup(funccall.getName().getValue());
            for (ASTNode argument :
                    funccall.getArguments()) {
                visit(argument);
            }
        } catch (ClassCastException e) {
            throw new CallingNonFunctionException("Attempting to call `" + funccall.getName().getValue() + "` when it is not a function.");
        }
        return null;
    }


    private void invalidTypeException(String varName, String typeName) throws SymbolNotTypeException {
        throw new SymbolNotTypeException("Can't declare `" + varName + "'. `" + typeName + "' is not a type ");
    }
}
