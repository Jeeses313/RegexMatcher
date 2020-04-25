package ui;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import regexmatcher.ui.TextUserInterface;
import static org.junit.Assert.*;

public class TextUserInterfaceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void canExit() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 1].startsWith("Exiting..."));
    }

    @Test
    public void canGiveWorkingRegularExpressionAndThenMatchStrings() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"aa", "n", "n", "aa", "ab", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 7].startsWith("Give string:"));
        assertTrue(output[output.length - 6].startsWith("String matches"));
        assertTrue(output[output.length - 4].startsWith("String does not match"));
    }

    @Test
    public void canChangeRegularExpression() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"aa", "n", "n", "-r", "ab", "n", "n", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 10].startsWith("Give regular expression:"));
        assertTrue(output[output.length - 7].startsWith("Give string:"));
        assertTrue(output[output.length - 6].startsWith("Give regular expression:"));
        assertTrue(output[output.length - 3].startsWith("Give string:"));
    }

    @Test
    public void canPrintInstructionsWhenGivingRegularExpression() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"-i", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 4].startsWith("Type -i at any time to get these instructions"));
    }

    @Test
    public void canPrintInstructionsWhenGivingStrings() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"aa", "n", "n", "-i", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 4].startsWith("Type -i at any time to get these instructions"));
    }

    @Test
    public void canPrintNFA() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"a*b", "y", "n", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 7].startsWith("State: 4 Is end: true"));
        assertTrue(output[output.length - 6].startsWith("Edges(char --> goal state):"));
    }

    @Test
    public void canPrintDFA() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"a*b", "n", "y", "y", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 7].startsWith("State: 2 Is end: true"));
        assertTrue(output[output.length - 6].startsWith("Edges(char --> goal state): "));
    }

    @Test
    public void cannotGiveStringaAfterGivingNonWorkingRegularExpression() {
        TextUserInterface tui = new TextUserInterface(new Scanner(join(new String[]{"[a-", "n", "n", "-q"})));
        tui.start();
        String[] output = outContent.toString().split("\n");
        assertTrue(output[output.length - 4].startsWith("Give real regular expression"));
    }

    private String join(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
