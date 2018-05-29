import automata.Automata;
import automata.TestAutomata;
import regex.RegExp;
import regex.TestRegExp;
import regex.Thompson;

import java.util.SortedSet;

public class main {

    public static void main(String[] args) {
//        System.out.println("TEST THOMPSON AND EPSILON CLOSURE");
//        testThompsonANDEpsilon();
//        System.out.println("");
//
//        System.out.println("TEST DFA TO NDFA");
//        testDFAtoNDFA();
//        System.out.println("");

        testToDFA();
    }

    private static void testAcceptInput( Automata automata,String input) {
        boolean accept = automata.accept(input);

        if(accept){
            System.out.println("----------------");
            System.out.println("    ACCEPTED!   ");
            System.out.println("----------------");
        }else{
            System.out.println("----------------");
            System.out.println("     DENIED!    ");
            System.out.println("----------------");
        }
    }

    public static void testToDFA(){
        Automata<Integer> ndfa = TestAutomata.testNDFATODFALESSON5();

        Automata<Integer> dfa = ndfa.NDFAtoDFA();
        dfa.printTransitions();
    }

    public static void testDFAtoNDFA(){
        System.out.println("----------------");
        System.out.println("     DFA    ");

        Automata<Integer> dfa = TestAutomata.getDFALesson4();
        dfa.printTransitions();

        System.out.println("----------------");
        System.out.println(" ");
        System.out.println("----------------");
        System.out.println("    NDFA    ");

        Automata<Integer> ndfa = dfa.DFAtoNDFA();
        ndfa.printTransitions();

        System.out.println("----------------");
        System.out.println(" ");
    }

    public static void testThompsonANDEpsilon(){
        TestRegExp tRegex = new TestRegExp();

//        RegExp start = tRegex.testThompson();

//        RegExp start = tRegex.testThompson2();

        RegExp start = tRegex.testThompson3();

        Thompson thompson = new Thompson();

        Automata<Integer> thompsonAutomata = thompson.parseAutomata(start);

        thompsonAutomata.printTransitions();

        char symbol = 'b';
        SortedSet<Integer> deltaESet = thompsonAutomata.deltaE(thompsonAutomata.getStartStates().first(), symbol);

        System.out.println(" ");
        System.out.println("----------------");
        System.out.println("DeltaE: "+ thompsonAutomata.getStartStates().first() + ", " + symbol);
        for(Integer i:deltaESet){
            System.out.println(i);
        }
        System.out.println("----------------");
        System.out.println(" ");
    }

    public static void testIsDFA() {
        Automata<String> dfa1 = TestAutomata.getDFALesson1();
//        FileIO.writeToFile(dfa1);
        System.out.println("--------------------");
        System.out.println("   DFA lesson 1  ");
        System.out.println("--------------------");

        System.out.println( "Is DFA? : < " +dfa1.isDFA() + " >");
        dfa1.printTransitions();

        Automata<String> ndfa1 = TestAutomata.getNDFALesson1();

        System.out.println("--------------------");
        System.out.println("   NDFA lesson 1  ");
        System.out.println("--------------------");

        System.out.println( "Is DFA? : < " +ndfa1.isDFA() + " >");
        ndfa1.printTransitions();


        Automata<String> exampleSlide14Lesson2 = TestAutomata.getExampleSlide14Lesson2();
        System.out.println("--------------------");
        System.out.println("   slide14 lesson 2 ");
        System.out.println("--------------------");

        exampleSlide14Lesson2.printTransitions();


    }



}
