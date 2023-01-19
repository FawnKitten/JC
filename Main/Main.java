package Main;

import Visitors.SymbolTableVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Main {

    @SuppressWarnings("CommentedOutCode")
    public static void main(String[] args) {
        // Main.main TODO: read code from arbitrary file, and add repl option
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
        Jc jc = new Jc(readFromFile("file.c"));
        jc.setVisitor(new SymbolTableVisitor());
        jc.eval();
        ((SymbolTableVisitor)jc.getVisitor()).dumpSymbolTable();
    }

    private static String readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            String res = "";
            while (fileReader.hasNextLine())
                res += fileReader.nextLine();
            return res;
        } catch (FileNotFoundException e) {
            System.out.println("Repl::readFromFile: " + fileName + " not found");
            return "";
        }
    }

}
