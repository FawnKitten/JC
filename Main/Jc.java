package Main;

import Visitors.*;
import Exceptions.*;

public class Jc {
    private final Parser parser;
    private NodeVisitor visitor;

    public Jc(String text) {
        this.parser = new Parser(text);
        this.visitor = new InterpretVisitor();
    }

    public void setVisitor(NodeVisitor visitor) {
        this.visitor = visitor;
    }

    public NodeVisitor getVisitor() { return visitor; }

    public void eval() {
        try {
            parser.generateTree();
            System.out.println("Generated Tree");
            visitor.setTree(parser.getTree());
            visitor.eval();
        } catch (InvalidSyntaxException e) {
            System.out.println("Main.Jc::eval: Main.Parser could not parse input");
            e.printStackTrace();
        } catch (InvalidCharacterException e) {
            System.out.println("Main.Jc::eval: Main.Lexer does not recognize character");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Main.Jc::eval: Main.Parser could not generate tree");
            e.printStackTrace();
        } catch (InvalidNodeTypeException e) {
            System.out.println("Main.Jc::eval: Node unhandled by visitor");
            e.printStackTrace();
        } catch (InterpretException e) {
            System.out.println("Main.Jc::eval: There was a problem with the execution of the program");
            e.printStackTrace();
        } catch (UndefinedSymbolException e) {
            System.out.println("Main.Jc::eval: Undefined symbol");
            e.printStackTrace();
        } catch (RedeclaredSymbolException e) {
            System.out.println("Main.Jc::eval: Symbol already defined");
            e.printStackTrace();
        } catch (SymbolException e) {
            System.out.println("Main.Jc::eval: There was a semantic error");
            e.printStackTrace();
        }
    }
}
