import automata.Automata;
import automata.TestAutomata;
import fileservice.FileIO;
import regex.RegExp;
import regex.TestRegExp;
import regex.Thompson;
import reggram.TestRegGram;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class main {

    public static void main(String[] args) {
        try {
            System.out.println("---------------------------------------------------------");
            System.out.println("ASSESSMENT PROJECT RICK VAN FESSEM EN KEVIN VAN DEN BROEK");
            System.out.println("DATUM: 12-06-2018                                        ");
            System.out.println("Alles is gemaakt, behalve woorden genereren die NIET in \ntaal zitten");
            System.out.println();
            System.out.println("Per opdracht wordt gewacht op een input. Press enter key to continue.");
            System.out.println("---------------------------------------------------------");

            System.in.read();
            System.out.println(" OPDRACHT 1 A       -       Hardcoded NDFA & DFA");
            OPDRACHT_1_A();
            System.in.read();
            System.out.println();
            System.out.println(" OPDRACHT 1 B       -       Regex parser");
            System.out.println(" HANDLEIDING: FOLDER: input. BESTAND: regexes.txt");
            System.out.println(" Op iedere newline kan een regex geschreven worden.");
            System.out.println();
            OPDRACHT_1_B();
            System.in.read();
            System.out.println(" OPDRACHT 2 A       -       Lijst met geaccepteerde woorden");
            OPDRACHT_2_A();
            System.in.read();
            System.out.println(" OPDRACHT 2 B       -       DFA - Testen of woord geaccepteerd wordt of niet.");
            OPDRACHT_2_B();
            System.in.read();
            System.out.println(" OPDRACHT 3 A       -       DFA - Constructor operaties -> Begint met, eindigt op, bevat");
            OPDRACHT_3_A();
            System.in.read();
            System.out.println(" OPDRACHT 3 B       -       DFA - EN / OF / NIET operaties");
            OPDRACHT_3_B();
            System.in.read();
            System.out.println(" OPDRACHT 4         -       Thompson constructie");
            OPDRACHT_4();
            System.in.read();
            System.out.println(" OPDRACHT 5         -       NDFA -> DFA met Epsilon overgangen");
            OPDRACHT_5();
            System.in.read();
            System.out.println(" OPDRACHT 6 A       -       Minimalisatie Brzozoswki");
            OPDRACHT_6_A();
            System.in.read();
            System.out.println(" OPDRACHT 6 B       -        Minimalisatie Hopcroft");
            OPDRACHT_6_B();
            System.in.read();
            System.out.println(" OPDRACHT 7         -        Gelijkheid van 2 regex's");
            OPDRACHT_7();
            System.in.read();
            System.out.println(" OPDRACHT 8         -        Graphviz koppeling");
            OPDRACHT_8();
            System.in.read();
            System.out.println(" OPDRACHT 9 A, B    -        NDFA -> Grammatica, Grammatica -> NDFA");
            OPDRACHT_9_A();
            System.in.read();
            System.out.println();
            System.out.println("-----------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void OPDRACHT_9_A() {
        //gram -> ndfa, testreggram1,2
        //NDFA -> gram, TestRegGram1,2
        TestRegGram.getRegGram1();
        TestRegGram.getRegGram2();
    }

    private static void OPDRACHT_8() {
        //Graphviz
        System.out.println("Alle verschillende opdrachten hebben een graphviz integratie gebruikt.");
        System.out.println("In de folder output zijn alle graphviz afbeeldingen te vinden.");
    }

    private static void OPDRACHT_7() {
        //Gelijkheid regexes.

        //RegexEqual1, RegexEqual2

        RegExp regExp = TestRegExp.regexEqual1();
        RegExp regExp1 = TestRegExp.regexEqual2();
        boolean equals = regExp.equals(regExp1);
        if (equals) {
            System.out.println("-------------------");
            System.out.println("    EQUAL !      ");
            System.out.println("-------------------");
        } else {
            System.out.println("-------------------");
            System.out.println("    NOT EQUAL !      ");
            System.out.println("-------------------");
        }
    }

    private static void OPDRACHT_6_B() {
        //Minimalisatie via hopcroft
        Thompson thompson = new Thompson();
        RegExp regExp = TestRegExp.testAplusB();
        Automata automata = thompson.parseAutomata(regExp).NDFAtoDFA().hopcroft();
        automata.printInfo();
        FileIO.writeToFile(automata);
    }

    private static void OPDRACHT_6_A() {
        //Minimalisatie via brzozoswki
        Thompson thompson = new Thompson();
        RegExp regExp = TestRegExp.testAplusB();
        Automata automata = thompson.parseAutomata(regExp).NDFAtoDFA().brzozowski();

        automata.printInfo();
        FileIO.writeToFile(automata);
    }

    private static void OPDRACHT_5() {
        //Thompson construcite naar DFA
        Thompson thompson = new Thompson();
        RegExp regExp = TestRegExp.testAplusB();

        Automata automata = thompson.parseAutomata(regExp).NDFAtoDFA();
        automata.printInfo();
        FileIO.writeToFile(automata);
    }

    private static void OPDRACHT_4() {
        //Thompson constructue, REGEX.
        Thompson thompson = new Thompson();
        RegExp regExp = TestRegExp.testAplusB();
        Automata automata = thompson.parseAutomata(regExp);
        automata.printInfo();
        FileIO.writeToFile(automata);
    }

    private static void OPDRACHT_3_B() throws IOException {
        //AND, OR, DENIAL DFA (DFA2, DFA3)
        Automata<Integer> automata2 = TestAutomata.DFA_2();
        Automata<Integer> automata3 = TestAutomata.DFA_3();
        System.out.println("AND :");
        automata2.and(automata3).printInfo();
        System.in.read();

        System.out.println("OR :");
        automata2.or(automata3).printInfo();
        System.in.read();

        System.out.println("DENIAL :");
        automata2.denial().printInfo();
        automata3.denial().printInfo();
        System.in.read();

    }

    private static void OPDRACHT_3_A() throws IOException {
        //Constructor testen, begint ab etc.
        System.out.println("Starts with ab: ");
        Automata.STARTS_WITH("ab");
        System.in.read();
        System.out.println("Contains ab: ");
        Automata.CONTAINS("ab");
        System.in.read();
        System.out.println("Ends with ab: ");
        Automata.ENDS_WITH("ab");
        System.in.read();
        System.out.println();
    }

    private static void OPDRACHT_2_B() {
        //Woord wel geaccepteerd of niet
        testAcceptInput(TestAutomata.DFA_1(), "ab");
        testAcceptInput(TestAutomata.DFA_1(), "ababb");
    }

    private static void OPDRACHT_2_A() {
        RegExp regExp = TestRegExp.testAplusB();
        System.out.println(regExp.getAcceptedWords());
        System.out.println();
    }

    private static void OPDRACHT_1_B() {
        List<RegExp> regExps = FileIO.readRegexFromFile(Paths.get("./input/regexes.txt"));
    }

    private static void OPDRACHT_1_A() throws IOException {
        System.out.println("Drie NDFA's :");

        System.out.println(" NDFA 1 ");
        TestAutomata.NDFA_1().printInfo();
        System.in.read();
        System.out.println(" NDFA 2 ");
        TestAutomata.NDFA_2().printInfo();
        System.in.read();
        System.out.println(" NDFA 3 ");
        TestAutomata.NDFA_3().printInfo();
        System.in.read();

        System.out.println("Drie DFA's :");
        System.out.println(" DFA 1");
        TestAutomata.DFA_1().printInfo();
        System.in.read();
        System.out.println(" DFA 2");
        TestAutomata.DFA_2().printInfo();
        System.in.read();
        System.out.println(" DFA 3");
        TestAutomata.DFA_3().printInfo();

    }

    private static void testAcceptInput(Automata automata, String input) {
        boolean accept = automata.accept(input);

        if (accept) {
            System.out.println("----------------");
            System.out.println("    ACCEPTED!   ");
            System.out.println("----------------");
        } else {
            System.out.println("----------------");
            System.out.println("     DENIED!    ");
            System.out.println("----------------");
        }
    }

}
