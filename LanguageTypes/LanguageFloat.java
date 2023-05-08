package LanguageTypes;

public class LanguageFloat extends LanguageType {
    private final float number;
    public float getNumber() { return number; }

    public LanguageFloat(float _number) { number = _number; }

    public String toString() {
        return "LanguageFloat(" + number + ")";
    }

    @Override
    public LanguageType plus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageFloat(
                number + ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number + ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.plus] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType minus(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageFloat(
                    number - ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number - ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.minus] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType divide(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageFloat(
                    number / ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number / ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.divide] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public LanguageType times(LanguageType other) {
        if (other instanceof LanguageInteger)
            return new LanguageFloat(
                    number * ((LanguageInteger)other).getNumber());
        else if (other instanceof LanguageFloat)
            return new LanguageFloat(
                    number * ((LanguageFloat)other).getNumber());
        else
            throw new RuntimeException(
                    "[LanguageFloat.times] LanguageInteger or LanguageFloat expected");
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        else
            return this.getNumber() == ((LanguageFloat) that).getNumber();
    }
}
