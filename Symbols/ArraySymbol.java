package Symbols;

public class ArraySymbol extends Symbol {

    private final TypeSymbol type;
    private final Integer size;

    public ArraySymbol(String name, TypeSymbol type, Integer size) {
        super(name);
        this.type = type;
        this.size = size;
    }

    public TypeSymbol getType() {
        return type;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "ArraySymbol(" + getName() + ", " + type + ", " + size + ')';
    }
}
