package Main;

import ASTNodes.*;
import Exceptions.InvalidCharacterException;
import Exceptions.InvalidSyntaxException;

import java.util.ArrayList;

@SuppressWarnings("CommentedOutCode")
public class Parser {
    private final Lexer lexer;
    private Token currentToken;
    private ASTNode tree;

    public Parser(String text) {
        lexer = new Lexer(text);
    }

    public ASTNode getTree() { return tree; }

    public void generateTree() throws InvalidSyntaxException, InvalidCharacterException {
        currentToken = lexer.consumeNextToken();
        tree = compoundStatements();
    }

//    private ASTNode block() {
//        // Parser.block() TODO: Implement block
//        return null;
//    }

    private ASTNode assignment()
            throws InvalidSyntaxException, InvalidCharacterException {
        Token name_tok = advanceToken(Token.Type.NAME);
        advanceToken(Token.Type.EQUALS);
        ASTNode value = numericalExpression();
        return new VariableAssignment(name_tok, value);
    }

    private ArrayList<ASTNode> declarations()
            throws InvalidSyntaxException, InvalidCharacterException {
        ArrayList<ASTNode> vardecs = new ArrayList<>();
        while (currentToken.getType() == Token.Type.NAME && lexer.peekToken().getType() == Token.Type.NAME) {
            Token type = advanceToken(Token.Type.NAME);
            if (type.getValue().equals("void"))
                throw new InvalidSyntaxException("`void` is not a valid variable type");
            vardecs.addAll(variable_declaration(type));
            advanceToken(Token.Type.SEMI_COLON);
        }
        return vardecs;
    }

    private ArrayList<ASTNode> variable_declaration(Token type)
            throws InvalidSyntaxException, InvalidCharacterException {
        // Main.Parser TODO: add behaviour for initialization
        ArrayList<ASTNode> vars = new ArrayList<>();
        Token name = advanceToken(Token.Type.NAME);
        vars.add(new VariableDeclaration(name, type));
        while (currentToken.getType() == Token.Type.COMMA) {
            advanceToken(Token.Type.COMMA);
            name = advanceToken(Token.Type.NAME);
            vars.add(new VariableDeclaration(name, type));
        }
        return vars;
    }

    private ASTNode numericalExpression()
            throws InvalidSyntaxException, InvalidCharacterException {
        ASTNode op = numericalTerm();
        while (currentToken.getType() == Token.Type.PLUS ||
               currentToken.getType() == Token.Type.DASH) {
            Token tok = advanceToken(Token.Type.PLUS, Token.Type.DASH);
            ASTNode right = numericalTerm();
            op = new BinaryOperator(tok, op, right);
        }
        return op;
    }

    private ASTNode numericalTerm() throws InvalidSyntaxException, InvalidCharacterException {
        ASTNode op = numericalFactor();
        while (currentToken.getType() == Token.Type.SLASH ||
               currentToken.getType() == Token.Type.STAR) {
            Token tok = advanceToken(Token.Type.SLASH, Token.Type.STAR);
            ASTNode right = numericalFactor();
            op = new BinaryOperator(tok, op, right);
        }
        return op;
    }

    private ASTNode numericalFactor() throws InvalidSyntaxException, InvalidCharacterException {
        Token tok = advanceToken(Token.Type.DASH, Token.Type.PLUS,
                Token.Type.LEFT_PAREN, Token.Type.INT_CONST,
                Token.Type.FLOAT_CONST, Token.Type.NAME);
        if (tok.getType() == Token.Type.DASH ||
            tok.getType() == Token.Type.PLUS) {
            return new UnaryOperator(tok, numericalFactor());
        } else if (tok.getType() == Token.Type.INT_CONST) {
            return new IntegerConstant(tok);
        } else if (tok.getType() == Token.Type.FLOAT_CONST) {
            return new FloatConstant(tok);
        }else if (tok.getType() == Token.Type.LEFT_PAREN) {
            ASTNode node = numericalExpression();
            advanceToken(Token.Type.RIGHT_PAREN);
            return node;
        } else if (tok.getType() == Token.Type.NAME) {
            return new VariableLookup(tok);
        } else {
            System.out.println("Main.Parser::numericalFactor: " + tok + " not implemented");
            return null; // to be implemented
        }
    }

    private ASTNode compoundStatements()
            throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.LEFT_CURLY);
        CompoundStatement comstat = new CompoundStatement();
        ArrayList<ASTNode> decs = declarations();
        comstat.addMultipleStatements(decs);
        while (currentToken.getType() != Token.Type.RIGHT_CURLY) {
            comstat.addStatement(statement());
            advanceToken(Token.Type.SEMI_COLON);
        }
        advanceToken(Token.Type.RIGHT_CURLY);
        return comstat;
    }

    private ASTNode statement() throws InvalidSyntaxException, InvalidCharacterException {
        // Main.Parser TODO: this could be a switch statement, for future refactor
        if (isOfType(currentToken, Token.Type.DASH, Token.Type.PLUS,
                Token.Type.LEFT_PAREN, Token.Type.INT_CONST))
            return numericalExpression();
        else if (isOfType(currentToken, Token.Type.NAME))
            return assignment();
        else if (isOfType(currentToken, Token.Type.KEY_WORD_IF)) {
            if (currentToken.getValue().equals("if")) return ifStatement();
        }
        return new NoOp();
    }

    private ASTNode ifStatement() throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.KEY_WORD_IF);
        advanceToken(Token.Type.LEFT_PAREN);
        ASTNode condition = numericalExpression();
        advanceToken(Token.Type.RIGHT_PAREN);
        CompoundStatement body = (CompoundStatement) compoundStatements();
        return new IfStatement(condition, body);
    }


    private Token advanceToken(Token.Type... types)
            throws InvalidSyntaxException, InvalidCharacterException {
        Token tok = new Token(currentToken);
        if (isOfType(tok, types)) {
            currentToken = lexer.consumeNextToken();
            return tok;
        }
        StringBuilder errTypes = new StringBuilder(types[0].toString());
        for (int i=1; i<types.length; i++)
            errTypes.append(", ").append(types[i].toString());
        int line = lexer.text.linePosition;
        int col = lexer.text.columnPosition;
        String indicator = col > 1 ? (" ").repeat(col-2) + "~^~" : "^~";
        String message = "Expected type " + errTypes + " but got type " + tok.getType() + " at " + line+":"+col + '\n' +
                '\t' + lexer.text.getLine() +
                '\t' + indicator;

        throw new InvalidSyntaxException(message);
    }

    private boolean isOfType(Token tok, Token.Type... types) {
        for (Token.Type type: types)
            if (tok.getType() == type)
                return true;
        return false;
    }
}
