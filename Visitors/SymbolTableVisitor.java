package Visitors;

import ASTNodes.*;
import Exceptions.*;
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
            declareBuiltinFunctions();
        } catch (SymbolException e) {
            // Should not happen under any circumstances
            // If this happens, the program should panic immediately
            throw new RuntimeException("[declareBuiltinFunctions.declareBuiltins] declaration called before the declaration of builtins");
        }
    }

    private void declareBuiltinTypes() throws RedeclaredSymbolException {
        symbolTable.declare(new TypeSymbol("int"));
        symbolTable.declare(new TypeSymbol("float"));
        symbolTable.declare(new TypeSymbol("String")); // delete when introduce char arrays
    }

    private void declareBuiltinFunctions() throws RedeclaredSymbolException, UndefinedSymbolException {
        TypeSymbol stringType = (TypeSymbol) symbolTable.lookup("String");
        symbolTable.declare(new FunctionSymbol("printf", List.of(stringType)));
        symbolTable.declare(new FunctionSymbol("DEBUG", List.of(stringType)));
    }

    @Override
    public Object visit(BinaryOperator binop)
            throws InterpretException, SymbolException {
        visit(binop.getLeft());
        visit(binop.getRight());
        return null;
    }

    @Override
    public Object visit(IntegerConstant num)
        { return null; }

    @Override
    public Object visit(FloatConstant num)
        { return null; }

    @Override
    public Object visit(UnaryOperator unop)
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
        symbolTable.define(varas.getName());
        visit(varas.getValue());
    }

    @Override
    public Object visit(VariableLookup varlo) throws SymbolException {
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
    public void visit(ArrayDeclaration arrdec) throws SymbolException {
        Symbol typeSymbol = symbolTable.lookup(arrdec.getCollectionType().getValue());
        try {
            TypeSymbol type = (TypeSymbol) typeSymbol;
            symbolTable.declare(new ArraySymbol(arrdec.getName().getValue(), type, arrdec.getSize()));
        } catch (ClassCastException e) {
            invalidTypeException(arrdec.getName().getValue(), typeSymbol.getName());
        }
    }

    @Override
    public void visit(ArrayAssignment arras) throws SymbolException, InterpretException {
        if (symbolTable.lookup(arras.getName().getValue()) instanceof ArraySymbol) {
            visit(arras.getValue());
        } else {
            throw new SymbolException("Attempt at indexing non-array symbol `"
                    + arras.getName().getValue() + "'");
        }
    }

    @Override
    public Object visit(ArrayLookup arrlo) throws SymbolException, InterpretException {
        if (symbolTable.lookup(arrlo.getName().getValue()) instanceof ArraySymbol) {
            visit(arrlo.getIndex());
        } else {
            throw new SymbolException("Attempt at indexing non-array symbol `"
                    + arrlo.getName().getValue() + "'");
        }
        return null;
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
    public Object visit(FunctionCall funccall) throws InterpretException, SymbolException {
        try {
            FunctionSymbol function = (FunctionSymbol) symbolTable.lookup(funccall.getName().getValue());
            for (ASTNode argument : funccall.getArguments()) {
                visit(argument);
            }
        } catch (ClassCastException e) {
            throw new CallingNonFunctionException("Attempting to call `" + funccall.getName().getValue() + "` when it is not a function.");
        }
        return null;
    }

    @Override
    public Object visit(StringConstant strconst) throws InterpretException, SymbolException {
        return null;
    }


    private void invalidTypeException(String varName, String typeName) throws SymbolNotTypeException {
        throw new SymbolNotTypeException("Can't declare `" + varName + "'. `" + typeName + "' is not a type ");
    }
}
