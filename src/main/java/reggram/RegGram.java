package reggram;

import automata.Automata;
import automata.Transition;

import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class RegGram <T extends Comparable> {

    private Set<TransitionGram <T>> transitions;
    private SortedSet<T> states;
    private SortedSet<T> startStates;
    private SortedSet<Character> alphabet;

    public RegGram()
    {
        this(new TreeSet<>());
    }

    public RegGram(Character [] s)
    {
        this(new TreeSet<>(Arrays.asList(s)) );
    }

    public RegGram(SortedSet<Character> alphabet)
    {
        transitions = new TreeSet<>();
        states = new TreeSet<T>();
        startStates = new TreeSet<T>();
        this.setAlphabet(alphabet);
    }

    public void setAlphabet(SortedSet<Character> symbols)
    {
        this.alphabet = symbols;
    }

    public SortedSet<Character> getAlphabet()
    {
        return alphabet;
    }

    public void addTransition(TransitionGram<T> t)
    {
        transitions.add(t);
        states.add(t.getFromState());
        if(t.getToState() != null) {
            states.add(t.getToState());
        }
    }

    public void defineAsStartState(T t)
    {
        // if already in states no problem because a Set will remove duplicates.
        states.add(t);
        startStates.add(t);
    }

    public void printTransitions()
    {
        Set<T> printedStates = new TreeSet<>();
        for (TransitionGram<T> t : transitions)
        {
            T state = t.getFromState();
            if(!printedStates.contains(state)){
                printedStates.add(state);
                System.out.print(state + " --> ");
                boolean isFirst = true;
                for(TransitionGram<T> transition: transitions){
                    if(transition.getFromState() == state){
                        if(isFirst) {
                            System.out.print(transition);
                            isFirst = false;
                        } else {
                            System.out.print( " | " + transition);
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    public void printInfo(){
        System.out.println("-------------------------");
        System.out.println("Startstates: " + startStates);
        System.out.println("States: " + states);
        System.out.println();
        printTransitions();
        System.out.println("-------------------------");
        System.out.println();
    }

    public Automata toNDFA(){
        Automata<Integer> ndfa = new Automata<>(getAlphabet());
        for(T Sstate : startStates){
            ndfa.defineAsStartState((Integer) Sstate);
        }

        for(TransitionGram t : transitions){
            if(t.getToState() != null) {
                ndfa.addTransition(new Transition(t.getFromState(), t.getSymbol(), t.getToState()));
            } else {
                int finalState = (Integer)states.last() + 1;
                ndfa.addTransition(new Transition(t.getFromState(), t.getSymbol(), finalState));
                ndfa.defineAsFinalState(finalState);
            }
        }

        return ndfa;
    }

}
