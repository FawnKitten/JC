import java.util.HashMap;

public class SymbolTable {

    enum Type {
        VARIABLE, TYPE, CONSTANT // SymboVisitor TODO: introduce function to symtable
    };

    private HashMap<String, Type> symTab = new HashMap<>();

}
