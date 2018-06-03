package automata;

/**
 * This file shows how to build up some example automata
 *
 * @author Kevin & Rick
 * @version 1.0
 */
public class TestAutomata
{

    static public Automata<Integer> getDFALesson4(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> dfa = new Automata<>(alphabet);

        dfa.addTransition(new Transition<Integer>(0, 'a'));
        dfa.addTransition(new Transition<Integer>(0, 'b', 1));

        dfa.addTransition(new Transition<Integer>(1, 'a', 0));
        dfa.addTransition(new Transition<Integer>(1, 'b', 2));

        dfa.addTransition(new Transition<Integer>(2, 'a', 0));
        dfa.addTransition(new Transition<Integer>(2, 'b'));

        dfa.defineAsStartState(0);

        dfa.defineAsFinalState(2);

        return dfa;
    }

    static public Automata<Integer> getNDFALesson4(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> dfa = new Automata<>(alphabet);

        dfa.addTransition(new Transition<Integer>(0, 'a'));
        dfa.addTransition(new Transition<Integer>(0, 'a', 1));
        dfa.addTransition(new Transition<Integer>(0, 'a', 2));

        dfa.addTransition(new Transition<Integer>(1, 'b', 0));

        dfa.addTransition(new Transition<Integer>(2, 'b', 1));
        dfa.addTransition(new Transition<Integer>(2, 'b'));

        dfa.defineAsStartState(2);

        dfa.defineAsFinalState(0);

        return dfa;
    }

    static public Automata<String> getDFALesson1()
    {
        Character [] alphabet = {'a', 'b'};
        Automata<String> m = new Automata<String>(alphabet);

        m.addTransition(new Transition<>("q0", 'a', "q1") );
        m.addTransition(new Transition<>("q0", 'b', "q4") );

        m.addTransition(new Transition<>("q1", 'a', "q4") );
        m.addTransition(new Transition<>("q1", 'b', "q2") );

        m.addTransition(new Transition<>("q2", 'a', "q3") );
        m.addTransition(new Transition<>("q2", 'b', "q4") );

        m.addTransition(new Transition<>("q3", 'a', "q1") );
        m.addTransition(new Transition<>("q3", 'b', "q2") );

        // the error state, loops for a and b:
        m.addTransition(new Transition<>("q4", 'a') );
        m.addTransition(new Transition<>("q4", 'b') );

        // only on start state in a dfa:
        m.defineAsStartState("q0");

        // two final states:
        m.defineAsFinalState("q2");
        m.defineAsFinalState("q3");

        return m;
    }

    public static Automata<String> getDFAstartAbbOrEndBaaB(){
        Character [] alphabet = {'a', 'b'};
        Automata<String> m = new Automata<String>(alphabet);

        m.addTransition(new Transition<>("q0", 'a', "q1") );
        m.addTransition(new Transition<>("q0", 'b', "q3") );

        m.addTransition(new Transition<>("q1", 'a', "q7") );
        m.addTransition(new Transition<>("q1", 'b', "q2") );

        m.addTransition(new Transition<>("q2", 'a', "q7") );
        m.addTransition(new Transition<>("q2", 'b', "q3") );

        m.addTransition(new Transition<>("q3", 'a', "q4") );
        m.addTransition(new Transition<>("q3", 'b', "q3") );

        m.addTransition(new Transition<>("q4", 'a', "q5") );
        m.addTransition(new Transition<>("q4", 'b', "q3") );

        m.addTransition(new Transition<>("q5", 'a', "q8") );
        m.addTransition(new Transition<>("q5", 'b', "q6") );

        m.addTransition(new Transition<>("q6", 'a', "q4"));
        m.addTransition(new Transition<>("q6", 'b', "q3"));

        m.addTransition(new Transition<>("q7", 'a') );
        m.addTransition(new Transition<>("q7", 'b', "q3") );

        m.addTransition(new Transition<>("q8", 'a') );
        m.addTransition(new Transition<>("q8", 'b') );


        // only on start state in a dfa:
        m.defineAsStartState("q0");

        // two final states:
        m.defineAsFinalState("q3");
        m.defineAsFinalState("q4");
        m.defineAsFinalState("q5");
        m.defineAsFinalState("q6");
        m.defineAsFinalState("q8");

        return m;
    }

    static public Automata<String> getNDFALesson1()
    {
        Character [] alphabet = {'a', 'b'};
        Automata<String> m = new Automata<String>(alphabet);

        m.addTransition(new Transition<>("q0", 'a', "q1") );
        m.addTransition(new Transition<>("q0", 'a', "q4") );
        m.addTransition(new Transition<>("q0", 'b', "q4") );

        m.addTransition(new Transition<>("q1", 'a', "q4") );
        m.addTransition(new Transition<>("q1", 'b', "q2") );

        m.addTransition(new Transition<>("q2", 'a', "q3") );
        m.addTransition(new Transition<>("q2", 'b', "q4") );

        m.addTransition(new Transition<>("q3", 'a', "q1") );
        m.addTransition(new Transition<>("q3", 'b', "q2") );

        // the error state, loops for a and b:
        m.addTransition(new Transition<>("q4", 'a') );
        m.addTransition(new Transition<>("q4", 'b') );

        // only on start state in a dfa:
        m.defineAsStartState("q0");

        // two final states:
        m.defineAsFinalState("q2");
        m.defineAsFinalState("q3");

        return m;
    }

    
    static public Automata<String> getExampleSlide14Lesson2()
    {
        Character [] alphabet = {'a', 'b'};
        Automata<String> m = new Automata<String>(alphabet);
        
        m.addTransition( new Transition<String> ("A", 'a', "C") );
        m.addTransition( new Transition<String> ("A", 'b', "B") );
        m.addTransition( new Transition<String> ("A", 'b', "C") );
        
        m.addTransition( new Transition<String> ("B", 'b', "C") );
        m.addTransition( new Transition<String> ("B", "C") );

        m.addTransition( new Transition<String> ("C", 'a', "D") );
        m.addTransition( new Transition<String> ("C", 'a', "E") );
        m.addTransition( new Transition<String> ("C", 'b', "D") );

        m.addTransition( new Transition<String> ("D", 'a', "B") );
        m.addTransition( new Transition<String> ("D", 'a', "C") );

        m.addTransition( new Transition<String> ("E", 'a') );
        m.addTransition( new Transition<String> ("E", "D") );

        // only on start state in a dfa:
        m.defineAsStartState("A");
        
        // two final states:
        m.defineAsFinalState("C");
        m.defineAsFinalState("E");
        
        return m;
    }

    static public Automata<String> testNDFA()
    {
        Character [] alphabet = {'a', 'b'};
        Automata<String> m = new Automata<String>(alphabet);

        m.addTransition(new Transition<>("q0", 'a', "q1") );
        m.addTransition(new Transition<>("q0", 'a', "q2") );

        m.addTransition(new Transition<>("q1", 'b', "q0") );

        m.addTransition(new Transition<>("q2", 'a', "q2") );
        m.addTransition(new Transition<>("q2", 'a', "q3") );

        m.addTransition(new Transition<>("q3", 'a', "q0") );
        m.addTransition(new Transition<>("q3", 'b', "q1") );
        m.addTransition(new Transition<>("q3", 'a', "q3") );

        // only on start state in a dfa:
        m.defineAsStartState("q0");

        // two final states:
        m.defineAsFinalState("q0");
        m.defineAsFinalState("q3");

        return m;
    }

    static public Automata<Integer> testNDFATODFALESSON5(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> a = new Automata<>(alphabet);

        a.addTransition(new Transition<>(1, 'a', 2));
        a.addTransition(new Transition<>(1, 'b', 2));
        a.addTransition(new Transition<>(1, 'b', 3));

        a.addTransition(new Transition<>(2, 'a', 3));
        a.addTransition(new Transition<>(2, 'b', 4));
        a.addTransition(new Transition<>(2, Transition.EPSILON, 4));

        a.addTransition(new Transition<>(3, 'a', 2));

        a.addTransition(new Transition<>(4, 'a', 2));
        a.addTransition(new Transition<>(4, 'a', 5));

        a.addTransition(new Transition<>(5, 'b', 5));
        a.addTransition(new Transition<>(5, Transition.EPSILON, 3));

        a.defineAsStartState(1);

        a.defineAsFinalState(2);
        a.defineAsFinalState(3);

        return a;
    }

    static public Automata<Integer> testNDFATODFALESSON5_1(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> a = new Automata<>(alphabet);

        a.addTransition(new Transition<>(0, 'a', 0));
        a.addTransition(new Transition<>(0, 'a', 1));
        a.addTransition(new Transition<>(0, 'b', 1));

        a.addTransition(new Transition<>(1, 'a', 2));
        a.addTransition(new Transition<>(1, 'b', 2));

        a.addTransition(new Transition<>(2, 'a', 0));
        a.addTransition(new Transition<>(2, 'a', 2));

        a.defineAsStartState(0);

        a.defineAsFinalState(0);

        return a;
    }

    static public Automata<Integer> testNDFATODFALESSON5_2(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> a = new Automata<>(alphabet);

        a.addTransition(new Transition<>(0, Transition.EPSILON, 1));
        a.addTransition(new Transition<>(0, Transition.EPSILON, 7));

        a.addTransition(new Transition<>(1, Transition.EPSILON, 2));
        a.addTransition(new Transition<>(1, Transition.EPSILON, 4));

        a.addTransition(new Transition<>(2, 'a', 3));

        a.addTransition(new Transition<>(3, Transition.EPSILON, 6));

        a.addTransition(new Transition<>(4, 'b', 5));

        a.addTransition(new Transition<>(5, Transition.EPSILON, 6));

        a.addTransition(new Transition<>(6, Transition.EPSILON, 7));
        a.addTransition(new Transition<>(6, Transition.EPSILON, 1));

        a.addTransition(new Transition<>(7, 'a', 8));

        a.addTransition(new Transition<>(8, 'b', 9));

        a.addTransition(new Transition<>(9, 'b', 10));

        a.defineAsStartState(0);

        a.defineAsFinalState(10);

        return a;
    }

    static public Automata<Integer> L1(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> a = new Automata<>(alphabet);

        a.addTransition(new Transition<>(1, 'a', 2));
        a.addTransition(new Transition<>(1, 'b', 1));

        a.addTransition(new Transition<>(2, 'a', 1));
        a.addTransition(new Transition<>(2, 'b', 2));

        a.defineAsStartState(1);

        a.defineAsFinalState(1);

        return a;
    }

    static public Automata<Integer> L2(){
        Character[] alphabet = {'a', 'b'};
        Automata<Integer> a = new Automata<>(alphabet);

        a.addTransition(new Transition<>(1, 'a', 1));
        a.addTransition(new Transition<>(1, 'b', 2));

        a.addTransition(new Transition<>(2, 'a', 1));
        a.addTransition(new Transition<>(2, 'b', 3));

        a.addTransition(new Transition<>(3, 'a', 1));
        a.addTransition(new Transition<>(3, 'b', 4));

        a.addTransition(new Transition<>(4, 'a', 4));
        a.addTransition(new Transition<>(4, 'b', 4));

        a.defineAsStartState(1);

        a.defineAsFinalState(1);
        a.defineAsFinalState(2);

        return a;
    }
    
}
