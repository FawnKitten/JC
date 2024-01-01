package Main;

import Visitors.InterpretVisitor;
import Visitors.PrintVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Main {

    @SuppressWarnings("CommentedOutCode")
    public static void main(String[] args) {
        // Main.main TODO: add repl option
        /* Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("> ");
                Main.Jc jc = new Main.Jc(scanner.nextLine());
                // jc.setVisitor(new PrintVisitor());
                jc.eval();
            } catch (NoSuchElementException e) {
                System.out.println("bye.");
                break;
            }
        } */
        Jc jc;
        if (args.length > 1)
            jc = new Jc(readFromFile(args[0]));
        else
            jc = new Jc(readFromFile("file.c"));
        jc.setVisitor(new InterpretVisitor());
        jc.eval();
    }

    private static String readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            StringBuilder res = new StringBuilder();
            while (fileReader.hasNextLine())
                res.append(fileReader.nextLine()).append('\n');
            return res.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Repl::readFromFile: " + fileName + " not found");
            return "";
        }
    }

}
