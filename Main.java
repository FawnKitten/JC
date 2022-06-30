import java.util.*;
import java.io.*;

public class Main {
    public static class Number {
        float num;
        public Number(float num) { this.num = num; }
        public int add(int other_num) { return (int)num + other_num; }
        public float add(float other_num) { return num + other_num; }
    }

    public static void main(String[] args) {
        // Repl TODO: read code from file, and add repl option
        /* Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("> ");
                Jc jc = new Jc(scanner.nextLine());
                // jc.setVisitor(new PrintVisitor());
                jc.eval();
            } catch (NoSuchElementException e) {
                System.out.println("bye.");
                break;
            }
        } */
        Jc jc = new Jc(readFromFile("file.c"));
        // jc.setVisitor(new PrintVisitor());
        jc.eval();
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
