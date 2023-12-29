package Symbols;

import java.util.List;

public class FunctionSymbol extends Symbol {

    private List<TypeSymbol> argumentTypes;

    public FunctionSymbol(String name, List<TypeSymbol> argumentTypes) {
        super(name);
        this.argumentTypes = argumentTypes;
    }

    public void setArgumentTypes(List<TypeSymbol> argumentTypes) {
        this.argumentTypes = argumentTypes;
    }

    @Override
    public String toString() {
        return "FunctionSymbol("  + getName() + ", " + argumentTypes + ")";
    }

}
