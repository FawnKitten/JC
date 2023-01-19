package LanguageTypes;

public interface LanguageType {
    LanguageType plus(LanguageType other);
    LanguageType minus(LanguageType other);
    LanguageType divide(LanguageType other);
    LanguageType times(LanguageType other);
}
