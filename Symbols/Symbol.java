package Symbols;
public abstract class Symbol {

    Symbol(String name) {
        this.name = name;
    }

    private String name;
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public abstract String toString();
}
