import automata.Automata;
import automata.TestAutomata;

public class main {

    public static void main(String[] args) {
      testIsDFA();
      testAcceptInput(TestAutomata.getDFALesson1(),"ababababababa");

      System.out.println(TestAutomata.getDFAstartAbbOrEndBaaB().isDFA());
      testAcceptInput(TestAutomata.getDFAstartAbbOrEndBaaB(), "ababbbbab");
        TestAutomata.getDFAstartAbbOrEndBaaB().printTransitions();
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

    public static void testIsDFA() {
        System.out.println("Works.");

        Automata<String> dfa1 = TestAutomata.getDFALesson1();

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
