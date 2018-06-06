import automata.Automata;
import automata.TestAutomata;
import fileservice.FileIO;
import regex.RegExp;
import regex.TestRegExp;
import regex.Thompson;
import reggram.TestRegGram;

import java.nio.file.Paths;
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

//        testToDFA();
//        testToDFAThompsons();
//        testDenial();
//        testAnd();
//        testRegExpEqual();
//        testHopcroft();

//        TestRegGram.getRegGram2();
//        TestAutomata.ndfaToRegGram();
            FileIO.readRegexFromFile(Paths.get("./input/regexes.txt"));
//        testToDFA();
//        testToDFAThompsons();
//        testDenial();
//        testAnd();
//        testRegExpEqual();

//        TestRegExp.testAplusB();

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
        System.out.println("----------------");
        System.out.println("    NDFA    ");
        Automata<Integer> ndfa = TestAutomata.testNDFATODFALESSON5();
        ndfa.printTransitions();
        System.out.println();
        System.out.println(ndfa.getStartStates());
        System.out.println(ndfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("     DFA    ");
        Automata dfa = ndfa.NDFAtoDFA();
        dfa.printTransitions();
        System.out.println();
        System.out.println(dfa.getStartStates());
        System.out.println(dfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("   Brzozowski   ");
        Automata simpleDfa = dfa.brzozowski();
        simpleDfa.printTransitions();
        System.out.println();
    }

    public static void testToDFAThompsons(){
        System.out.println("----------------");
        System.out.println("    NDFA    ");
        TestRegExp tRegex = new TestRegExp();
        RegExp start = tRegex.testThompson4();

        Thompson thompson = new Thompson();
        Automata<Integer> ndfa = thompson.parseAutomata(start);
        ndfa.printTransitions();
        System.out.println();
        System.out.println(ndfa.getStartStates());
        System.out.println(ndfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("     DFA    ");
        Automata dfa = ndfa.NDFAtoDFA();
        dfa.printTransitions();
//        FileIO.writeToFile(dfa);
        System.out.println();
        System.out.println(dfa.getStartStates());
        System.out.println(dfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("    SDFA    ");
        Automata simpleDfa = dfa.brzozowski();
        simpleDfa.printTransitions();
        System.out.println();
        System.out.println(simpleDfa.getStartStates());
        System.out.println(simpleDfa.getFinalStates());
        System.out.println();
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

        RegExp start = tRegex.testThompson();

//        RegExp start = tRegex.testThompson2();
//
//        RegExp start = tRegex.testThompson3();

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

    public static void testDenial(){
        Automata<String> dfa = TestAutomata.getDFALesson1();
        dfa.printInfo();

        System.out.println();

        Automata notDfa = dfa.denial();
        notDfa.printInfo();
    }

    public static void testAnd(){
        Automata dfa1 = TestAutomata.L1();
        Automata dfa2 = TestAutomata.L2();

        Automata result = dfa1.and(dfa2);
        result.printInfo();
        Automata result2 = dfa1.or(dfa2);
        result2.printInfo();
    }

    public static void testRegExpEqual(){
        TestRegExp tRegex = new TestRegExp();
        RegExp r1 = tRegex.regexEqual1();
        RegExp r2 = tRegex.regexEqual2();

        boolean result = r1.equals(r2);

        System.out.println("r1 == r2: " + result);
    }

    public static void testHopcroft(){
        Automata ndfa = TestAutomata.testNDFATODFALESSON5();
        ndfa.printTransitions();
        System.out.println();
        System.out.println(ndfa.getStartStates());
        System.out.println(ndfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("     DFA    ");
        Automata dfa = ndfa.NDFAtoDFA();
        dfa.printTransitions();
        System.out.println();
        System.out.println(dfa.getStartStates());
        System.out.println(dfa.getFinalStates());
        System.out.println();

        System.out.println("----------------");
        System.out.println("    Hopcroft    ");
        Automata simpleDfa = dfa.hopcroft();
        simpleDfa.printTransitions();
        System.out.println();
        System.out.println(simpleDfa.getStartStates());
        System.out.println(simpleDfa.getFinalStates());
        System.out.println();
    }


    //mainmethod for the assessment
    public static void Assessment(){
        //TEST DFA
        System.out.println("----------------------");
        System.out.println("--      3 DFA's     --");
        //TODO Add 3 DFA's
        System.out.println("----------------------");
        System.out.println();

        //Test NDFA
        System.out.println("----------------------");
        System.out.println("--      3 NDFA's    --");
        //TODO add 3 NDFA's
        System.out.println("----------------------");
        System.out.println();

        //Test RegExp
        System.out.println("----------------------");
        System.out.println("--     3 RegExp     --");
        //TODO add 3 RegExp
        System.out.println("----------------------");
        System.out.println();
    }



}
