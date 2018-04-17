package automata;
/**
 * The class automata.Automata represents both DFA and NDFA: some NDFA's are also DFA
 * Using the method isDFA we can check this
 * 
 * We use '$' to denote the empty symbol epsilon
 * 
 * @author Paul de Mast
 * @version 1.0

 */

import java.util.*;

public class Automata<T extends Comparable>
{

    // Or use a Map structure
    private Set<Transition <T>> transitions;

    private SortedSet<T> states;
    private SortedSet<T> startStates;
    private SortedSet<T> finalStates;
    private SortedSet<Character> symbols;

    public Automata()
    {
           this(new TreeSet<>());
    }
    
    public Automata(Character [] s)
    {   
        this(new TreeSet<>(Arrays.asList(s)) );
    }

    public Automata(SortedSet<Character> symbols)
    {
        transitions = new TreeSet<>();
        states = new TreeSet<T>();
        startStates = new TreeSet<T>();
        finalStates = new TreeSet<T>();
        this.setAlphabet(symbols);
    }
    
    public void setAlphabet(Character [] s)
    {
        this.setAlphabet(new TreeSet<>(Arrays.asList(s)));
    }
    
    public void setAlphabet(SortedSet<Character> symbols)
    {
       this.symbols = symbols;
    }
    
    public SortedSet<Character> getAlphabet()
    {
       return symbols;
    }
    
    public void addTransition(Transition<T> t)
    {
        transitions.add(t);
        states.add(t.getFromState());
        states.add(t.getToState());        
    }
    
    public void defineAsStartState(T t)
    {
        // if already in states no problem because a Set will remove duplicates.
        states.add(t);
        startStates.add(t);        
    }

    public void defineAsFinalState(T t)
    {
        // if already in states no problem because a Set will remove duplicates.
        states.add(t);
        finalStates.add(t);        
    }

    public void printTransitions()
    {

        for (Transition<T> t : transitions)
        {
            System.out.println (t);
        }
    }
    //Determinite finite automata
    public boolean isDFA()
    {
        boolean isDFA = true;
        
        for (T from : states)
        {
            for (char symbol : symbols)
            {
                isDFA = getToStates(from, symbol) == 1;
                if(!isDFA)
                    return isDFA;
            }
        }
        
        return isDFA;
    }

    private long getToStates(final T from, final char symbol) {
        return transitions.stream()
                .filter(tTransition ->
                        tTransition.getSymbol() == symbol
                        && tTransition.getFromState().equals(from))
                .count();
    }

}
