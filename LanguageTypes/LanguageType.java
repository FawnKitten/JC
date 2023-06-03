package LanguageTypes;

public abstract class LanguageType {
    public abstract LanguageType plus(LanguageType other);
    public abstract LanguageType minus(LanguageType other);
    public abstract LanguageType divide(LanguageType other);
    public abstract LanguageType times(LanguageType other);
    @Override
    public abstract boolean equals(Object that);

    @Override
    public abstract String toString();

    public abstract LanguageInteger compareTo(LanguageType that);
}
