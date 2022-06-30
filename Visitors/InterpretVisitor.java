import java.util.HashMap;

public class InterpretVisitor extends NodeVisitor {
    private HashMap<String, LanguageType> variables = new HashMap<>();

    public InterpretVisitor(ASTNode tree) { super(tree); }

    public InterpretVisitor() { super(); }

    public HashMap<String, LanguageType> getVariables() { return variables; }

    @Override
    public void eval() {
        visit(getTree());
        System.out.println("Variables:");
        System.out.println(variables.toString());
        System.out.println("");
    }

    @Override
    public LanguageType visit(BinaryOperator binop)
        throws NullPointerException, ClassCastException {
        LanguageType left = (LanguageType)visit(binop.getLeft());
        LanguageType right = (LanguageType)visit(binop.getRight());
        if (left == null) {
            throw new NullPointerException(
                    "Uninitalized variable "
                    + ((VariableLookup)(binop.getLeft())).getName()
                    + ".");
                    // + "(had the value of"")");
        } else if (right == null) {
            throw new NullPointerException(
                    "Uninitalized variable "
                    + ((VariableLookup)(binop.getRight())).getName()
                    + ".");
                    // + "(had the value of"")");
        }
        switch (binop.getToken().getType()) {
            case PLUS: return left.plus(right);
            case DASH: return left.minus(right);
            case SLASH: return left.divide(right);
            case STAR: return left.times(right);
        }
        throw new RuntimeException("[InterpretVisitor.visit(BinaryOperator)] type not handled");
    }


    @Override
    public LanguageType visit(IntegerConstant intconst) {
        return new LanguageInteger(intconst.getValue());
    }

    @Override
    public LanguageType visit(FloatConstant floatconst) {
        return new LanguageFloat(floatconst.getValue());
    }

    @Override
    public LanguageType visit(UnaryOperator unop)
        throws NullPointerException, ClassCastException {
        LanguageInteger zero = new LanguageInteger(0);
        LanguageType value = (LanguageType)visit(unop.getNode());
        switch (unop.getToken().getType()) {
           case PLUS: return zero.plus(value);
           case DASH: return zero.minus(value);
        }
        throw new RuntimeException("[InterpretVisitor.visit(UnaryOperator)] type not handled");
    }

    @Override
    public void visit(CompoundStatement comstat)
        throws NullPointerException, ClassCastException {
        for (ASTNode stat: comstat.getStatements())
            visit(stat);
    }

    @Override
    public void visit(VariableAssignment varas)
        throws NullPointerException, ClassCastException {
        LanguageType value = (LanguageType)visit(varas.getValue());
        String name = varas.getName();
        // System.out.println("*** " + name + " <- " + value.toString());
        // for debuging purpouses uncomment
        variables.put(name, value);
    }

    @Override
    public LanguageType visit(VariableLookup varlo) {
        String name = varlo.getName().getValue();
        LanguageType value = variables.get(name);
        // System.out.println("*** " + name + " -> " + value.toString());
        // for debuging purpouses uncomment
        return value;
    }

    @Override
    public void visit(VariableDeclaration vardec) {
        // InteretVisitor TODO: symbol table visitor
        String name = vardec.getName().getValue();
        String type = vardec.getType().getValue();
        // System.out.println("*** " + name + " := " + type);
        // for debuging purpouses uncomment
        variables.put(name, null);
    }

}
