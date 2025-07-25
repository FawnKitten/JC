package Main;

import ASTNodes.*;
import Exceptions.InvalidCharacterException;
import Exceptions.InvalidSyntaxException;
import java.util.ArrayList;

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
        while (currentToken.getType() == Token.Type.NAME
            && lexer.peekToken().getType() == Token.Type.NAME) {
            Token type = advanceToken(Token.Type.NAME);
            if (type.getValue().equals("void"))
                throw new InvalidSyntaxException("`void` is not a valid variable type");
            vardecs.addAll(variableAndArrayDeclarations(type));
            advanceToken(Token.Type.SEMI_COLON);
        }
        return vardecs;
    }

    private ArrayList<ASTNode> variableAndArrayDeclarations(Token type)
            throws InvalidSyntaxException, InvalidCharacterException {
        // Main.Parser TODO: add behaviour for initialization
        var vars = new ArrayList<ASTNode>();
        Token name = advanceToken(Token.Type.NAME);
        if (currentToken.getType() == Token.Type.LEFT_BRACKET) {
            advanceToken(Token.Type.LEFT_BRACKET);
            Token size = advanceToken(Token.Type.INT_CONST);
            advanceToken(Token.Type.RIGHT_BRACKET);
            vars.add(new ArrayDeclaration(name, type, size));
        } else {
            vars.add(new VariableDeclaration(name, type));
        }
        while (currentToken.getType() == Token.Type.COMMA) {
            advanceToken(Token.Type.COMMA);
            name = advanceToken(Token.Type.NAME);
            if (currentToken.getType() == Token.Type.LEFT_BRACKET) {
                advanceToken(Token.Type.LEFT_BRACKET);
                Token size = advanceToken(Token.Type.INT_CONST);
                advanceToken(Token.Type.RIGHT_BRACKET);
                vars.add(new ArrayDeclaration(name, type, size));
            } else {
                advanceToken(Token.Type.COMMA);
                vars.add(new VariableDeclaration(name, type));
            }
        }
        return vars;
    }

    private ASTNode booleanExpression()
            throws InvalidSyntaxException, InvalidCharacterException {
        // TODO: Consider Refactoring Somehow
        ASTNode left = booleanTerm();
        while (isOfType(currentToken,
                Token.Type.BOOL_AND,
                Token.Type.BOOL_OR
        )) {
            Token token = advanceToken(
                    Token.Type.BOOL_AND,
                    Token.Type.BOOL_OR
            );
            ASTNode right = booleanTerm();
            left = new BinaryOperator(token, left, right);
        }
        return left;
    }

    private ASTNode expression()
        throws InvalidSyntaxException, InvalidCharacterException {
        if (isOfType(currentToken, Token.Type.STRING_CONST))
            return stringExpression();
        else
            return booleanExpression();
    }
    private ASTNode booleanTerm()
            throws InvalidSyntaxException, InvalidCharacterException {
       ASTNode left = booleanFactor();
       // TODO: Consider Refactoring Somehow
       if (isOfType(currentToken,
           Token.Type.BOOL_EQUALS,
           Token.Type.BOOL_GREATER,
           Token.Type.BOOL_LESSER
       )) {
           Token token = advanceToken(
               Token.Type.BOOL_EQUALS,
               Token.Type.BOOL_GREATER,
               Token.Type.BOOL_LESSER
           );
           ASTNode right = booleanFactor();
           return new BinaryOperator(token, left, right);
       }
       return left;
    }

    private ASTNode booleanFactor()
            throws InvalidSyntaxException, InvalidCharacterException {
        if (isOfType(currentToken, Token.Type.BOOL_NOT)) {
            Token token = advanceToken(Token.Type.BOOL_NOT);
            ASTNode expression = numericalExpression();
            return new UnaryOperator(token, expression);
        } else {
            return numericalExpression();
        }
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
            ASTNode node = booleanExpression();
            advanceToken(Token.Type.RIGHT_PAREN);
            return node;
        } else if (tok.getType() == Token.Type.NAME
                && currentToken.getType() == Token.Type.LEFT_BRACKET) {
            advanceToken(Token.Type.LEFT_BRACKET);
            ASTNode index = numericalExpression();
            advanceToken(Token.Type.RIGHT_BRACKET);
            return new ArrayLookup(tok, index);
        } else if (tok.getType() == Token.Type.NAME) {
            return new VariableLookup(tok);
        } else {
            System.out.println("Main.Parser::numericalFactor: " + tok + " not implemented");
            return null; // to be implemented
        }
    }

    private boolean isBlockStatement(ASTNode statement) {
        return (statement instanceof IfStatement ||
                statement instanceof CompoundStatement ||
                statement instanceof WhileStatement);
        // When introduce for, while, function statements include here
    }

    private ASTNode compoundStatements()
            throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.LEFT_CURLY);
        var comstat = new CompoundStatement();
        ArrayList<ASTNode> decs = declarations();
        comstat.addMultipleStatements(decs);
        while (currentToken.getType() != Token.Type.RIGHT_CURLY) {
            comstat.addStatement(statement());
            if (!isBlockStatement(comstat.getStatements().getLast()))
                advanceToken(Token.Type.SEMI_COLON); // no need for semicolon after block statements
        }
        advanceToken(Token.Type.RIGHT_CURLY);
        return comstat;
    }

    private ASTNode statement() throws InvalidSyntaxException, InvalidCharacterException {
        // Main.Parser TODO: this could be a switch statement, for future refactor
        if (isOfType(currentToken, Token.Type.DASH, Token.Type.PLUS,
                Token.Type.LEFT_PAREN, Token.Type.INT_CONST))
            return numericalExpression();
        else if (isOfType(currentToken, Token.Type.NAME)
                && isOfType(lexer.peekToken(), Token.Type.LEFT_PAREN))
            return functionCallStatement();
        else if (isOfType(currentToken, Token.Type.NAME)
                && isOfType(lexer.peekToken(), Token.Type.EQUALS))
            return assignment();
        else if (isOfType(currentToken, Token.Type.NAME)
                && isOfType(lexer.peekToken(), Token.Type.LEFT_BRACKET))
            return arrayAssignment();
        else if (isOfType(currentToken, Token.Type.STRING_CONST))
            return stringExpression();
        else if (isOfType(currentToken, Token.Type.KEY_WORD_IF)) {
            if (currentToken.getValue().equals("if")) return ifStatement();
        } else if (isOfType(currentToken, Token.Type.KEY_WORD_WHILE))
            return whileStatement();
        return new NoOp();
    }

    private ASTNode arrayAssignment() throws InvalidCharacterException, InvalidSyntaxException {
        var name = advanceToken(Token.Type.NAME);
        advanceToken(Token.Type.LEFT_BRACKET);
        var index = numericalExpression();
        advanceToken(Token.Type.RIGHT_BRACKET);
        advanceToken(Token.Type.EQUALS);
        var value = expression();
        return new ArrayAssignment(name, index, value);
    }

    private ASTNode whileStatement()
            throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.KEY_WORD_WHILE);
        advanceToken(Token.Type.LEFT_PAREN);
        ASTNode condition = booleanExpression();
        advanceToken(Token.Type.RIGHT_PAREN);
        ASTNode body = compoundStatements();
        return new WhileStatement(condition, body);
    }

    private ASTNode ifStatement()
            throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.KEY_WORD_IF);
        advanceToken(Token.Type.LEFT_PAREN);
        ASTNode condition = booleanExpression();
        advanceToken(Token.Type.RIGHT_PAREN);
        CompoundStatement body = (CompoundStatement) compoundStatements();
        CompoundStatement elseBody = null;
        if (currentToken.getType() == Token.Type.KEY_WORD_ELSE)
            elseBody = elseClause();
        return new IfStatement(condition, body, elseBody);
    }

    private CompoundStatement elseClause()
            throws InvalidSyntaxException, InvalidCharacterException {
        advanceToken(Token.Type.KEY_WORD_ELSE);
        return (CompoundStatement) compoundStatements();
    }

    private FunctionCall functionCallStatement()
            throws InvalidCharacterException, InvalidSyntaxException {
        Token name = advanceToken(Token.Type.NAME);
        advanceToken(Token.Type.LEFT_PAREN);
        ArrayList<ASTNode> arguments = new ArrayList<>();
        if (!isOfType(currentToken, Token.Type.RIGHT_PAREN)) {
            arguments.add(expression());
            while (!isOfType(currentToken, Token.Type.RIGHT_PAREN)) {
                advanceToken(Token.Type.COMMA);
                arguments.add(expression());
            }
            advanceToken(Token.Type.RIGHT_PAREN);
        }
        System.out.println("name of function: " + name);
        System.out.println("Arguments: " + arguments);
        return new FunctionCall(name, arguments);
    }

    private ASTNode stringExpression()
            throws  InvalidSyntaxException, InvalidCharacterException {
        Token token = advanceToken(Token.Type.STRING_CONST);
        String value = token.getValue();
        return new StringConstant(token, value);
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
        int line = lexer.text.linePosition + 1;
        int col = lexer.text.columnPosition + 1;
        String indicator = col > 1 ? (" ").repeat(col-2) + "~^~" : "^~";
        String message = "Expected type "+errTypes+" but got type "+tok.getType()+" at "+line+":"+col + '\n' +
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
