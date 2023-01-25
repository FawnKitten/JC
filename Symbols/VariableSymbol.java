package Symbols;

public class VariableSymbol extends Symbol {

    public VariableSymbol(String name, TypeSymbol type) {
        super(name);
        this.state = State.DECLARED;
        this.type = type;
    }

    public enum State {
        DECLARED, INITIALIZED
    }

    private State state;
    public void setState(State state) { this.state = state; }
    public State getState() { return state; }

    private final TypeSymbol type;
    public TypeSymbol getType() { return type; }

    @Override
    public String toString() {
        return "VariableSymbol(" + getName() + ", " + type + ", " + state + ")";
    }

}
