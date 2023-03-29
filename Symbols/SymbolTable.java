package Symbols;

import Exceptions.RedeclaredSymbolException;
import Exceptions.UndefinedSymbolException;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private final HashMap<String, Symbol> symTab = new HashMap<>();

    public void declare(Symbol symbol)
        throws RedeclaredSymbolException {
        // do not allow symbols to be redeclared
        if (has(symbol.getName()))
            throw new RedeclaredSymbolException(
                    "Error: " + symbol.getName() + " has already been declared");
        symTab.put(symbol.getName(), symbol);
    }

    public Symbol lookup(String symbolName)
        throws UndefinedSymbolException {
        if (!symTab.containsKey(symbolName))
            throw new UndefinedSymbolException("Error: Undefined Symbol `" + symbolName + "'");
        return symTab.get(symbolName);
    }

    public void define(String symbolName) {
        VariableSymbol variableSymbol = (VariableSymbol) symTab.get(symbolName);
        variableSymbol.setState(VariableSymbol.State.INITIALIZED);
    }

    public void dumpContents() {
        for (Map.Entry<String, Symbol> symbol : symTab.entrySet())
            System.out.println(symbol.getKey() + ": " + symbol.getValue());
    }

    @Override
    public String toString() {
        return "SymbolTable(" + symTab + ")";
    }

    public boolean has(String symbolName) {
        return symTab.containsKey(symbolName);
    }
}
