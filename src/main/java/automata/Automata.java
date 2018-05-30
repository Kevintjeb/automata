package automata;
/**
 * The class automata.Automata represents both DFA and NDFA: some NDFA's are also DFA
 * Using the method isDFA we can check this
 * 
 * We use '$' to denote the empty symbol epsilon
 *
 * @author Kevin & Rick
 * @version 1.0
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

    public T getLastFromStates(){
        return states.last();
    }

    public SortedSet<T> getStartStates(){return startStates;}

    public SortedSet<T> getFinalStates(){ return finalStates;}

    public SortedSet<T> getStates(){ return states;}

    private void setStartStates(SortedSet<T> newStartStates){ startStates = newStartStates;}

    private void setFinalStates(SortedSet<T> newFinalStates){ finalStates = newFinalStates;}

    private void setStates(SortedSet<T> newStates){ states = newStates;}

    public T getFinalState(){
        return finalStates.first();
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

    public Set<Transition<T>> getAllTransitions(){
        return transitions;
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

    private T toState(Automata a, final T from, final char symbol) {
        List<Transition<T>> result = a.getTransitions(from);
        for(Transition t : result){
            if(t.getSymbol() == symbol)
                return (T) t.getToState();
        }
        return null;
    }

    public SortedSet<T> deltaE(T state, char symbol){
        SortedSet<T> result;

        SortedSet<T> startSet = new TreeSet<>();
        startSet.add(state);
        result = eClosure(startSet);
        result = delta(result, symbol);
        result = eClosure(result);

        return result;
    }

    private SortedSet<T> eClosure(SortedSet<T> states){
        SortedSet<T> result = new TreeSet<>();
        eClosureRecursive(states, result);
        return result;
    }

    private SortedSet<T> eClosureRecursive(SortedSet<T> states, SortedSet<T> result){
        for(T s: states){
            result.add(s);
            List<Transition<T>> transitionsForS = getTransitions(s);
            for(Transition<T> t: transitionsForS){
                if(t.getSymbol() == Transition.EPSILON){
                    int resultLength = result.size();
                    result.add(t.getToState());
                    if(result.size() > resultLength) {
                        SortedSet<T> symbolSet = new TreeSet<>();
                        symbolSet.add(t.getToState());
                        result.addAll(eClosureRecursive(symbolSet, result));
                    }
                }
            }
        }
        return result;
    }

    private SortedSet<T> delta(SortedSet<T> states, char symbol){
        SortedSet<T> result = new TreeSet<>();

        for(T s: states){
            List<Transition<T>> transitionsForS = getTransitions(s);
            for(Transition<T> t: transitionsForS){
                if(t.getSymbol() == symbol){
                    result.add(t.getToState());
                }
            }
        }
        return result;
    }

    public Automata DFAtoNDFA(){
        Automata ndfa = new Automata();

        //if automata is already NDFA, no convertion needed
        if(isDFA()){

            //convert dfa to ndfa with reverse
            ndfa.setAlphabet(getAlphabet());
            ndfa.setStartStates(getFinalStates());
            ndfa.setFinalStates(getStartStates());
            ndfa.setStates(getStates());

            //copy transitions reverse
            Iterator<Transition<T>> transitionIterator = getAllTransitions().iterator();
            while(transitionIterator.hasNext()) {
                Transition<T> trans = transitionIterator.next();
                ndfa.addTransition(new Transition<>(trans.getToState(), trans.getSymbol(), trans.getFromState()));
            }
        }
        else{
            ndfa = this;
        }

        return ndfa;
    }

    public Automata NDFAtoDFA(){
        Automata dfa = new Automata();

        //if automata is already DFA, no convertion needed
        if(!isDFA()){
            //convert dfa to ndfa

            //create table with every state and the reachable states
            Map<T, ArrayList<SortedSet<T>>> eplisonClosureAllStates = new LinkedHashMap<>();
            Map<T, Integer> lookupRenameTable = new LinkedHashMap<>();

            //create lookup table eclosure per state
            for(final Iterator it = getStates().iterator(); it.hasNext();){
                ArrayList<SortedSet<T>> perState = new ArrayList<>();
                T state = (T) it.next();
                for (final Iterator alphabetIt = getAlphabet().iterator(); alphabetIt.hasNext(); ) {
                    char symbol = (char) alphabetIt.next();
                    perState.add(deltaE(state, symbol));
                }
                eplisonClosureAllStates.put(state, perState);
            }

            //create the startstate from all the startstates
            SortedSet<T> startState = new TreeSet<>();
            for(final Iterator itStartStates = getStartStates().iterator(); itStartStates.hasNext();){
                T state = (T) itStartStates.next();
                SortedSet<T> states = new TreeSet<>();
                states.add(state);
                startState.addAll(eClosure(states));
            }

            //set alphabet
            dfa.setAlphabet(getAlphabet());
            //define the firststartstate
            dfa.defineAsStartState(renameState((T) stateToString(startState), lookupRenameTable));

            //find all transitions
            Map<SortedSet<T>, ArrayList<SortedSet<T>>> paths = findPaths(startState, eplisonClosureAllStates);

            //create transitions
            //foreach state
            for(Map.Entry<SortedSet<T>, ArrayList<SortedSet<T>>> entry : paths.entrySet()){
                ArrayList<SortedSet<T>> t = entry.getValue();
                int counter = 0;
                //foreach arraylist, so foreach symbol
                for(SortedSet<T> end : t){
                    int counter2 = 0;
                    //foreach symbol in alphabet. Find the matching symbol from the arraylist with the symbol of the alphabet
                    for(final Iterator alphabetIt = getAlphabet().iterator(); alphabetIt.hasNext();) {
                        char symbol = (char) alphabetIt.next();
                        if(counter == counter2) //if both symbols matches
                        {
                            dfa.addTransition(new Transition(renameState((T) stateToString(entry.getKey()), lookupRenameTable), symbol , renameState((T)stateToString(end), lookupRenameTable)));
                            //check if state is a endState and define endState
                            for(Iterator itEnd = getFinalStates().iterator(); itEnd.hasNext();){
                                if(entry.getKey().contains(itEnd.next())){
                                    dfa.defineAsFinalState(renameState((T)stateToString(entry.getKey()), lookupRenameTable));
                                }
                            }
                        }
                        counter2++;
                    }
                    counter++;
                }
            }
        }
        else{
            dfa = this;
        }

        return dfa;
    }

    Map<SortedSet<T>, ArrayList<SortedSet<T>>> result = new LinkedHashMap<>();
    private Map<SortedSet<T>, ArrayList<SortedSet<T>>> findPaths(SortedSet<T> state, Map<T, ArrayList<SortedSet<T>>> lookupTable){

        int counter = 0;
        ArrayList<SortedSet<T>> paths = new ArrayList<>();
        for(final Iterator alphabetIt = getAlphabet().iterator(); alphabetIt.hasNext();) {
            char symbol = (char) alphabetIt.next();
            paths.add(findPath(state, lookupTable, counter));
            counter ++;
        }
        result.put(state, paths);

        for(SortedSet<T> path: paths){
            if(!result.containsKey(path)) {
                result.putAll(findPaths(path, lookupTable));
            }
        }

        return result;
    }

    private SortedSet<T> findPath(SortedSet<T> state, Map<T, ArrayList<SortedSet<T>>> lookupTable, int counter){
        SortedSet<T> result = new TreeSet<>();
        for(Iterator it = state.iterator(); it.hasNext();){
            T s = (T) it.next();
            for(Map.Entry<T, ArrayList<SortedSet<T>>> entry : lookupTable.entrySet()){
                if(entry.getKey() == s){
                    result.addAll(entry.getValue().get(counter));
                }
            }
        }

        return result;
    }


    private String stateToString(Set<T> state){
        String result = "[";
        for(final Iterator it = state.iterator(); it.hasNext();){
            T s = (T) it.next();
            if(it.hasNext()){
                result += s + ",";
            } else {
                result += s + "]";
            }
        }
        return result;
    }

    private String stateToString(List<T> state){
        String result = "[";
        for(final Iterator it = state.iterator(); it.hasNext();){
            T s = (T) it.next();
            if(it.hasNext()){
                result += s + ",";
            } else {
                result += s + "]";
            }
        }
        return result;
    }

    public Automata brzozowski(){
        if(isDFA()){
            return DFAtoNDFA().NDFAtoDFA().DFAtoNDFA().NDFAtoDFA();
        } else {
            return NDFAtoDFA().DFAtoNDFA().NDFAtoDFA().DFAtoNDFA().NDFAtoDFA();
        }
    }

    private Integer renameState(T state, Map<T, Integer> lookupTable){
        if(!lookupTable.containsKey(state)){
            //rename state
            Collection<Integer> values = lookupTable.values();
            int newValue = 0;
            if(values.size() > 0) {
                //already values in the map
                int lastValue = (int) values.toArray()[values.size() - 1];
                newValue = lastValue+1;
                lookupTable.put(state, newValue);
                return newValue;
            } else {
                //no values in the map
                lookupTable.put(state, newValue);
                return newValue;
            }
        } else {
            return lookupTable.get(state);
        }
    }

    public Automata denial(){
        if(isDFA()) {
            Automata result = new Automata(this.getAlphabet());

            result.startStates = this.startStates;
            result.states = this.states;
            result.transitions = this.transitions;
            SortedSet finalStates = new TreeSet<>(this.states);
            finalStates.removeAll(this.finalStates);
            result.finalStates = finalStates;

            return result;
        }
        return null;
    }

    public void printInfo(){
        System.out.println("-------------------------");
        System.out.println("Is a DFA: " + isDFA());
        System.out.println("Startstates: " + startStates);
        System.out.println("States: " + states);
        System.out.println("Finalstates: " + finalStates);
        System.out.println();
        printTransitions();
        System.out.println("-------------------------");
        System.out.println();
    }

    public Automata and(Automata a2){
        //test is both are DFA otherwise and isn't possible
        if(isDFA() && a2.isDFA()){
            Automata result = andOR(a2, true);
            return result;
        }
        return  null;
    }

    public Automata or(Automata a2){
        //test is both are DFA otherwise and isn't possible
        if(isDFA() && a2.isDFA()){
            Automata result = andOR(a2, false);
            return result;
        }
        return  null;
    }

    private Automata andOR(Automata a2, boolean isAnd){
        Automata result = new Automata();
        Map<T, Integer> lookupRenameTable = new LinkedHashMap<>();

        //create the startstate
        List<T> startState = new ArrayList<>();
        startState.addAll(this.getStartStates());
        startState.addAll(a2.getStartStates());

        result.defineAsStartState(stateToString(startState));

        Map<List<T>, List<List<T>>> paths = new LinkedHashMap<>();
        getAndList(startState, a2, paths);

        for(Map.Entry<List<T>, List<List<T>>> entry : paths.entrySet()){
            List<List<T>> t = entry.getValue();
            int counter = 0;
            //foreach arraylist, so foreach symbol
            for(List<T> end : t){
                int counter2 = 0;
                //foreach symbol in alphabet. Find the matching symbol from the arraylist with the symbol of the alphabet
                for(final Iterator alphabetIt = getAlphabet().iterator(); alphabetIt.hasNext();) {
                    char symbol = (char) alphabetIt.next();
                    if(counter == counter2) //if both symbols matches
                    {
                        result.addTransition(new Transition(stateToString(entry.getKey()), symbol , stateToString(end)));
                        //check if state is a endState and define endState
                        if(isAnd){
                            //final states and
                            if(this.finalStates.contains(entry.getKey().get(0)) && a2.finalStates.contains(entry.getKey().get(1))){
                                result.defineAsFinalState(stateToString(entry.getKey()));
                            }
                        } else {
                            //final states or
                            if(this.finalStates.contains(entry.getKey().get(0)) || a2.finalStates.contains(entry.getKey().get(1))){
                                result.defineAsFinalState(stateToString(entry.getKey()));
                            }
                        }
                    }
                    counter2++;
                }
                counter++;
            }
        }
        return result;
    }

    private void getAndList(List<T> state, Automata a2, Map<List<T>, List<List<T>>> paths){
        List<List<T>> listStateTo = new ArrayList<>();
        for(final Iterator alphabetIt = getAlphabet().iterator(); alphabetIt.hasNext();){
            char symbol = (char) alphabetIt.next();
            List<T> newState = new ArrayList<>();
            newState.add(toState(this, state.get(0), symbol));
            newState.add(toState(a2, state.get(1), symbol));
            listStateTo.add(newState);
        }
        paths.put(state, listStateTo);

        //recursive till state already in path list
        for(List<T> s : listStateTo){
            if(!paths.containsKey(s)){
                getAndList((List<T>) s, a2, paths);
            }
        }
    }


}
