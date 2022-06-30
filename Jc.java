
public class Jc {
    private Parser parser;
    private NodeVisitor visitor;

    public Jc(String text) {
        this.parser = new Parser(text);
        this.visitor = new InterpretVisitor();
    }

    public void setVisitor(NodeVisitor visitor) {
        this.visitor = visitor;
    }

    public void eval() {
        try {
            parser.generateTree();
            visitor.setTree(parser.getTree());
            visitor.eval();
        } catch (InvalidSyntaxException e) {
            System.out.println("Jc::eval: parser could not parse input");
            e.printStackTrace();
        } catch (InvalidCharacterException e) {
            System.out.println("Jc::eval: lexer does not recognize character");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Jc::eval: parser could not generate tree");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("Jc::eval: Node unhandled by visitor");
            e.printStackTrace();
        }
    }
}
