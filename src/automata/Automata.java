package automata;
/**
 * The class automata.Automata represents both DFA and NDFA: some NDFA's are also DFA
 * Using the method isDFA we can check this
 * 
 * We use '$' to denote the empty symbol epsilon
 */

import java.util.*;
import java.util.stream.Collectors;

public class Automata<T extends Comparable>
{

    // Or use a Map structure
    private Set<Transition <T>> transitions;

    private SortedSet<T> states;
    private SortedSet<T> startStates;
    private SortedSet<T> finalStates;
    private SortedSet<Character> alphabet;

    public Automata()
    {
           this(new TreeSet<>());
    }
    
    public Automata(Character [] s)
    {   
        this(new TreeSet<>(Arrays.asList(s)) );
    }

    public Automata(SortedSet<Character> alphabet)
    {
        transitions = new TreeSet<>();
        states = new TreeSet<T>();
        startStates = new TreeSet<T>();
        finalStates = new TreeSet<T>();
        this.setAlphabet(alphabet);
    }
    
    public void setAlphabet(Character [] s)
    {
        this.setAlphabet(new TreeSet<>(Arrays.asList(s)));
    }
    
    public void setAlphabet(SortedSet<Character> symbols)
    {
       this.alphabet = symbols;
    }

    public void addAlphabetCharacter(char character) {
        this.alphabet.add(character);
    }

    public SortedSet<Character> getAlphabet()
    {
       return alphabet;
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
            for (char symbol : alphabet)
            {
                isDFA = getToStates(from, symbol) == 1;
                if(!isDFA)
                    return isDFA;
            }
        }
        
        return isDFA;
    }

    public boolean isInAlfabet(String input){
        char[] chars = input.toCharArray();
        List<Character> charsCollection = new ArrayList<>();
        for (char c:chars) {
            charsCollection.add(c);
        }
        return alphabet.containsAll(charsCollection);
    }

    public boolean accept(String input) {
        System.out.println("------------------");
        System.out.println("Verify : " + input);
        System.out.println("------------------");

        if(!isInAlfabet(input))
            return false;
        char[] chars = input.toCharArray();
        List<List<Transition<T>>> startStates = this.startStates
                .stream()
                .map(this::getTransitions)
                .collect(Collectors.toList());

        for (List<Transition<T>> startState: startStates) {
            List<Transition<T>> currentTransitions = startState;
            for (char c: chars) {
                Optional<Transition<T>> transitionForSymbol = getTransitionForSymbol(c, currentTransitions);
                currentTransitions = goToNextState(transitionForSymbol.get());
            }

            if(finalStates.contains(currentTransitions.get(0).getFromState())){
                return true;
            }
        }

        return false;
    }

    public Optional<Transition<T>> getTransitionForSymbol(char symbol, List<Transition<T>> transitions) {
        return transitions
                .stream()
                .filter(tTransition -> tTransition.getSymbol() == symbol)
                .findFirst();
    }

    public List<Transition<T>> getTransitions(T state){
        return transitions
                .stream()
                .filter(tTransition -> tTransition.getFromState().equals(state))
                .collect(Collectors.toList());
    }

    public List<Transition<T>> goToNextState(Transition<T> currentState) {
        return getTransitions(currentState.getToState());
    }

    private long getToStates(final T from, final char symbol) {
        return transitions.stream()
                .filter(tTransition ->
                        tTransition.getSymbol() == symbol
                        && tTransition.getFromState().equals(from))
                .count();
    }

}
