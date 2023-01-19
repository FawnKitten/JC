package Symbols;

public class VariableSymbol extends Symbol {

    public VariableSymbol(String name, Symbol type) {
        super(name);
        this.state = State.INITIALIZED;
        this.type = type;
    }

    public enum State {
        INITIALIZED, ASSIGNED
    };

    private State state;
    public void setState(State state) { this.state = state; }
    public State getState() { return state; }

    private Symbol type;
    public Symbol getType() { return type; }

    @Override
    public String toString() {
        return "VariableSymbol(" + getName() + ", " + type + ", " + state + ")";
    }

}
