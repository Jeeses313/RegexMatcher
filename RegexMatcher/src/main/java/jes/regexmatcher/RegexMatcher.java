package jes.regexmatcher;

import java.util.Scanner;

public class RegexMatcher {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Regular expression matcher");
        System.out.println("Syntax:");
        System.out.println("- *");
        System.out.println("- +");
        System.out.println("- ?");
        System.out.println("- |");
        System.out.println("- [a-z], [A-Z] and [0-9]");
        System.out.println("- ()");
        System.out.println("- .");
        System.out.println("Type %q% at any time to quit");
        System.out.println("Type %r% to change regular expression");
        program:
        while (true) {
            System.out.print("Give regular expression: ");
            String expression = reader.nextLine();
            if (expression.equals("%q%")) {
                break;
            }
            //Matcher matcher = new Matcher(expression)
            //v- tarkistetaan saatiinko automaatti tehtyä, esim. matcher.works()
            if (!expression.isEmpty()) {
                while (true) {
                    System.out.print("Give string: ");
                    String string = reader.nextLine();
                    if (string.equals("%q%")) {
                        break program;
                    }
                    if (string.equals("%r%")) {
                        break;
                    }
                    /*
                tarkistetaan kuuluuko merkkijono säännöllisen lausekkeen kieleen
                boolean matches = matcher.match(string)
                if(matches) {
                    System.out.println("String matches");
                } else {
                    System.out.println("String does not match");
                }
                     */
                }
            } else {
                System.out.println("Give real regular expression");
            }
        }
    }
}
