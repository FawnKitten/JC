
class TypeSymbol extends Symbol {

    TypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "TypeSymbol(" + getName() + ")";
    }

}
