package Main;

import Exceptions.InvalidCharacterException;

public class Lexer {
    private final String text;
    private int pos;
    private Character currentChar;

    // for now return types are language keywords
    // TODO: add types to symbol table
    private final String[] keyWords = {
        "int", "void", "float"
    };

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(0);
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public Token consumeNextToken() throws InvalidCharacterException {
        // Lexer.consumeNextToken TODO: remove main while since it does not loop
        while (currentChar != null) {
            skipSpace();
            if (Character.isDigit(currentChar)) {
                return nextNumber();
            } else if (currentChar == '+') {
                advanceCurrentChar();
                return new Token("+", Token.Type.PLUS);
            } else if (currentChar == '-') {
                advanceCurrentChar();
                return new Token("-", Token.Type.DASH);
            } else if (currentChar == '/') {
                advanceCurrentChar();
                return new Token("/", Token.Type.SLASH);
            } else if (currentChar == '*') {
                advanceCurrentChar();
                return new Token("*", Token.Type.STAR);
            } else if (currentChar == '(') {
                advanceCurrentChar();
                return new Token("(", Token.Type.LEFT_PAREN);
            } else if (currentChar == ')') {
                advanceCurrentChar();
                return new Token(")", Token.Type.RIGHT_PAREN);
            } /* else if (currentChar == '[') {
                advanceCurrentChar();
                return new Main.Token("[", Main.Token.Type.LEFT_BRACKET);
            } else if (currentChar == ']') {
                advanceCurrentChar();
                return new Main.Token("]", Main.Token.Type.RIGHT_BRACKET);
            } */ else if (currentChar == '{') {
                advanceCurrentChar();
                return new Token("{", Token.Type.LEFT_CURLY);
            } else if (currentChar == '}') {
                advanceCurrentChar();
                return new Token("}", Token.Type.RIGHT_CURLY);
            } else if (currentChar == ';') {
                advanceCurrentChar();
                return new Token(";", Token.Type.SEMI_COLON);
            } else if (Character.isAlphabetic(currentChar) || currentChar == '_') {
                return nextIdentifier();
            } else if (currentChar == '=') {
                advanceCurrentChar();
                return new Token("=", Token.Type.EQUALS);
            } else if (currentChar == ',') {
                advanceCurrentChar();
                return new Token(",", Token.Type.COMMA);
            } else {
                throw new InvalidCharacterException("Invalid character `" + currentChar + "'");
            }
        }
        return new Token("", Token.Type.NONE);
    }

    public Token nextNumber() {
        String res = "";
        while (currentChar != null && Character.isDigit(currentChar)) {
            res += currentChar;
            advanceCurrentChar();
        }
        if (currentChar != '.')
            return new Token(res, Token.Type.INT_CONST);
        else {
            res += currentChar;
            advanceCurrentChar();
            while (currentChar != null && Character.isDigit(currentChar)) {
                res += currentChar;
                advanceCurrentChar();
            }
            return new Token(res, Token.Type.FLOAT_CONST);
        }
    }

    public Token nextIdentifier() {
        String res = "";
        while (Character.isAlphabetic(currentChar) || currentChar == '_') {
            res += currentChar;
            advanceCurrentChar();
        }
        for (int i=0; i<keyWords.length; i++) {
            if (keyWords[i].equals(res))
                return new Token(res, Token.Type.KEY_WORD);
        }
        return new Token(res, Token.Type.NAME);
    }

    private void advanceCurrentChar() {
        if (pos + 1 < this.text.length()) {
            pos++;
            currentChar = text.charAt(pos);
        } else {
            currentChar = null;
        }
    }

    private void skipSpace() {
        while (currentChar != null && Character.isWhitespace(currentChar))
            advanceCurrentChar();
    }
}

