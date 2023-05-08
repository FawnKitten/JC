package Main;

import java.util.ArrayList;
import java.util.Collections;

public class Text implements Cloneable {
    public Text(String rawText) {
        this.text = rawText;
        for (String line: text.split("\n"))
            lines.add(line + "\n");
        currentChar = rawText.charAt(0);
    }
    private String text;
    private int absolutePosition = 0; // number of characters to the beginning (including new lines)
    private ArrayList<String> lines = new ArrayList<>();
    public int linePosition = 0; // what line number
    public int columnPosition = 0; // what column of the line
    private Character currentChar;
    public Character getCurrentChar() { return currentChar; }

    public String getRawText() { return text; }

    public void advancePosition() {
        if (absolutePosition+1 < text.length()) {
            absolutePosition++;
        }
        if (columnPosition + 1 < lines.get(linePosition).length())
            columnPosition++;
        else if (linePosition + 1 < lines.size()) {
            columnPosition = 0;
            linePosition++;
        } else {
            currentChar = null;
            return;
        }
        currentChar = lines.get(linePosition).charAt(columnPosition);
    }

    public Character nextChar() {
        if (absolutePosition+1 < text.length())
            return text.charAt(absolutePosition+1);
        else
            return null;
    }

    public String getLine() {
        return lines.get(linePosition);
    }

    @Override
    public Text clone() {
        try {
            Text clone = (Text) super.clone();
            clone.text = text;
            clone.absolutePosition = absolutePosition;
            clone.lines = lines;
            clone.linePosition = linePosition;
            clone.columnPosition = columnPosition;
            clone.currentChar = currentChar;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public char peekCharacter() {
        return text.charAt(absolutePosition+1);
    }
}
