import automata.Automata;
import automata.TestAutomata;

public class main {

    public static void main(String[] args) {
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
