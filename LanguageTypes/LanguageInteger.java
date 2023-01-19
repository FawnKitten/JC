package LanguageTypes;

public class LanguageInteger implements LanguageType {
    private int number;
    public int getNumber() { return number; }

    public LanguageInteger(int _number) { number = _number; }

    public String toString() {
        return "LanguageInteger(" + number + ")";
    }

    public LanguageType plus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number + ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number + ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.plus] LanguageInteger or LanguageFloat expected");
    }

    public LanguageType minus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number - ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number - ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.minus] LanguageInteger or LanguageFloat expected");
    }

    public LanguageType divide(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number / ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number / ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.divide] LanguageInteger or LanguageFloat expected");
    }

    public LanguageType times(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageInteger(
                    number * ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number * ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.times] LanguageInteger or LanguageFloat expected");
    }
}
