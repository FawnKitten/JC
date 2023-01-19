package Main;

public class Token {
    public enum Type {
        NONE, INT_CONST, FLOAT_CONST, PLUS,
        DASH, SLASH, STAR, LEFT_PAREN,
        RIGHT_PAREN, LEFT_BRACKET, RIGHT_BRACKET,
        LEFT_CURLY, RIGHT_CURLY, SEMI_COLON,
        NAME, KEY_WORD, EQUALS, COMMA
    }
    private String value;
    private Token.Type type;

    public Token(String value, Token.Type type) {
        this.value = value;
        this.type = type;
    }

    public Token(Token tok) {
        this.value = tok.getValue();
        this.type = tok.getType();
    }

    public String getValue() { return this.value; }
    public void setValue(String value) { this.value = value; }
    public Token.Type getType() { return this.type; }
    public void setType(Token.Type type) { this.type = type; }

    public String toString() {
        String type_name = type == null ? "null" : type.toString();
        String value_name = value == null ? "null" : value;
        return "Main.Token(" + value_name + ", " + type_name + ")";
    }
}

