package Symbols;

import Exceptions.RedeclaredSymbolException;
import Exceptions.UndefinedSymbolException;

import java.util.ArrayList;
import java.util.Collections;

public class ScopedSymbolTable {

    private final ArrayList<SymbolTable> scopeStack = new ArrayList<>();

    private SymbolTable currentScope;

    public ScopedSymbolTable() {
        enterScope(); // global scope
    }

    public void declare(Symbol symbol)
            throws RedeclaredSymbolException {
        // do not allow symbols to be redeclared
        if (currentScope.has(symbol.getName()))
            throw new RedeclaredSymbolException(
                    "Error: " + symbol.getName() + " has already been declared");
        currentScope.declare(symbol);
    }

    @SuppressWarnings("unchecked") // silence unchecked cast which will work
    public Symbol lookup(String symbolName)
            throws UndefinedSymbolException {
        ArrayList<SymbolTable> reversed =  (ArrayList<SymbolTable>) scopeStack.clone();
        Collections.reverse(reversed);
        for (SymbolTable symbolTable: reversed) {
            if (symbolTable.has(symbolName))
                return symbolTable.lookup(symbolName);
        }
        throw new UndefinedSymbolException("Error: Undefined Symbol `" + symbolName + "'");
    }

    @SuppressWarnings("unchecked")
    public void define(String symbolName) throws UndefinedSymbolException {
        ArrayList<SymbolTable> reversed = (ArrayList<SymbolTable>) scopeStack.clone();
        Collections.reverse(reversed);
        for (SymbolTable symbolTable: reversed) {
            if (symbolTable.has(symbolName)) {
                symbolTable.define(symbolName);
                return;
            }
        }
        throw new UndefinedSymbolException("Error: Undefined Symbol `" + symbolName + "'");
    }

    public void enterScope() {
        scopeStack.add(new SymbolTable());
        currentScope = scopeStack.get(scopeStack.size()-1);
    }

    public void exitScope() {
        System.out.println("Scope " + (scopeStack.size()-1));
        currentScope.dumpContents();
        scopeStack.remove(scopeStack.size()-1);
        currentScope = scopeStack.get(scopeStack.size()-1);
    }

    public void dumpContents() {
        for (int i=0; i< scopeStack.size(); i++) {
            System.out.println("Scope " + i);
            scopeStack.get(i).dumpContents();
        }
    }

    public ArrayList<SymbolTable> getScopeStack() {
        return scopeStack;
    }
}
