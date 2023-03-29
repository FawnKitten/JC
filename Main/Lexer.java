package Main;

import Exceptions.InvalidCharacterException;

public class Lexer {
    public Text text;

    // for now return types are language keywords
    // TODO: add types to symbol table
    private final String[] keyWords = {
        "if"
    };

    public Lexer(String text) {
        this.text = new Text(text);
    }


    public Token consumeNextToken() throws InvalidCharacterException {
        while (text.getCurrentChar() != null) {
            if (Character.isSpaceChar(text.getCurrentChar()) || text.getCurrentChar() == '\n') {
                skipSpace();
                continue;
            }
            if (matchComment()) {
                if (text.getCurrentChar() == '/' && text.nextChar() == '/') {
                    skipLineComment();
                } else if (text.getCurrentChar() == '/' && text.nextChar() == '*') {
                    skipBlockComment();
                }
                continue;
            }
            if (Character.isDigit(text.getCurrentChar())) {
                return nextNumber();
            } else if (text.getCurrentChar() == '+') {
                text.advancePosition();
                return new Token("+", Token.Type.PLUS);
            } else if (text.getCurrentChar() == '-') {
                text.advancePosition();
                return new Token("-", Token.Type.DASH);
            } else if (text.getCurrentChar() == '/') {
                text.advancePosition();
                return new Token("/", Token.Type.SLASH);
            } else if (text.getCurrentChar() == '*') {
                text.advancePosition();
                return new Token("*", Token.Type.STAR);
            } else if (text.getCurrentChar() == '(') {
                text.advancePosition();
                return new Token("(", Token.Type.LEFT_PAREN);
            } else if (text.getCurrentChar() == ')') {
                text.advancePosition();
                return new Token(")", Token.Type.RIGHT_PAREN);
            } /* else if (text.getCurrentChar() == '[') {
                text.advancePosition();
                return new Main.Token("[", Main.Token.Type.LEFT_BRACKET);
            } else if (text.getCurrentChar() == ']') {
                text.advancePosition();
                return new Main.Token("]", Main.Token.Type.RIGHT_BRACKET);
            } */ else if (text.getCurrentChar() == '{') {
                text.advancePosition();
                return new Token("{", Token.Type.LEFT_CURLY);
            } else if (text.getCurrentChar() == '}') {
                text.advancePosition();
                return new Token("}", Token.Type.RIGHT_CURLY);
            } else if (text.getCurrentChar() == ';') {
                text.advancePosition();
                return new Token(";", Token.Type.SEMI_COLON);
            } else if (Character.isAlphabetic(text.getCurrentChar()) || text.getCurrentChar() == '_') {
                return nextIdentifier();
            } else if (text.getCurrentChar() == '=') {
                text.advancePosition();
                return new Token("=", Token.Type.EQUALS);
            } else if (text.getCurrentChar() == ',') {
                text.advancePosition();
                return new Token(",", Token.Type.COMMA);
            }else {
                invalidCharacter();
            }
        }
        return new Token("", Token.Type.NONE);
    }

    private void invalidCharacter() throws InvalidCharacterException {
        int line = text.linePosition;
        int col = text.columnPosition;
        String message = "Invalid character at " + (line+1) + ":" + (col+1) + " `" + text.getCurrentChar() + "'" + '\n'+
                "\t" + text.getLine() +
                "\t" + (" ").repeat(col) + "^";
        throw new InvalidCharacterException(message);
    }

    private boolean matchComment() {
        return text.getCurrentChar() == '/' && (text.nextChar() == '/' || text.nextChar() == '*');
    }

    private void skipBlockComment() {
        while (text.getCurrentChar() != null && !(text.getCurrentChar() == '*' && text.nextChar() == '/'))
            text.advancePosition();
        // skip the '*' and the '/' characters
        text.advancePosition();
        text.advancePosition();
    }

    private void skipLineComment() {
        while (text.getCurrentChar() != null && text.getCurrentChar() != '\n')
            text.advancePosition();
    }

    public Token nextNumber() {
        StringBuilder res = new StringBuilder();
        while (text.getCurrentChar() != null && Character.isDigit(text.getCurrentChar())) {
            res.append(text.getCurrentChar());
            text.advancePosition();
        }
        if (text.getCurrentChar() == null || text.getCurrentChar() != '.')
            return new Token(res.toString(), Token.Type.INT_CONST);
        else {
            res.append(text.getCurrentChar());
            text.advancePosition();
            while (text.getCurrentChar() != null && Character.isDigit(text.getCurrentChar())) {
                res.append(text.getCurrentChar());
                text.advancePosition();
            }
            return new Token(res.toString(), Token.Type.FLOAT_CONST);
        }
    }

    public Token nextIdentifier() {
        StringBuilder res = new StringBuilder();
        while (Character.isAlphabetic(text.getCurrentChar()) || text.getCurrentChar() == '_') {
            res.append(text.getCurrentChar());
            text.advancePosition();
        }
        for (String keyWord : keyWords) {
            if (keyWord.equals(res.toString()))
                return new Token(res.toString(), Token.Type.valueOf("KEY_WORD_" + res.toString().toUpperCase()));
        }
        return new Token(res.toString(), Token.Type.NAME);
    }

    private void skipSpace() {
        while (text.getCurrentChar() != null &&
                (Character.isWhitespace(text.getCurrentChar()) || text.getCurrentChar() == '\n'))
            text.advancePosition();
    }

    public Token peekToken() throws InvalidCharacterException {
        Text currentText = text.clone();
        Token peek = consumeNextToken();
        text = currentText;
        return peek;
    }
}

