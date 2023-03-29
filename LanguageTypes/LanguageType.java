package LanguageTypes;

public abstract class LanguageType {
    public abstract LanguageType plus(LanguageType other);
    public abstract LanguageType minus(LanguageType other);
    public abstract LanguageType divide(LanguageType other);
    public abstract LanguageType times(LanguageType other);
}
