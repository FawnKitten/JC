package LanguageTypes;

public class LanguageInteger extends LanguageType {
    private final int number;
    public int getNumber() { return number; }

    public LanguageInteger(int _number) { number = _number; }

    public String toString() {
        return "LanguageInteger(" + number + ")";
    }

    @Override
    public LanguageType plus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number + ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number + ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageInteger.plus] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType minus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number - ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number - ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageInteger.minus] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType divide(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number / ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number / ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageInteger.divide] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType times(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number * ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number * ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageInteger.times] LanguageInteger or LanguageFloat expected");
    }
    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        else
            return this.getNumber() == ((LanguageInteger) that).getNumber();
    }

    @Override
    public LanguageInteger compareTo(LanguageType that) {
        return new LanguageInteger((getNumber() > ((LanguageInteger)that).getNumber()) ? 1 : 0);
    }
}
