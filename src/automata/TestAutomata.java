package automata;

/**
 * This file shows how to build up some example automata
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestAutomata
{

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
    
}
