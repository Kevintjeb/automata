package regex;

import automata.Automata;
import automata.Transition;

import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Class to construct the Thompson construction
 *
 *
 * @author Kevin & Rick
 * @version 1.0
 */

public class Thompson {
    private Stack<Automata> automataStack = new Stack<>();

    Map<Automata, Integer> automatasPerLayer = new LinkedHashMap<>();

    public Thompson() {
    }

    private void toAutomata(RegExp regex, Integer layer) {

        automatasPerLayer.put(operatorToAutomata(regex), layer);

        if (regex.left != null){
            int templayerLeft = layer;
            templayerLeft++;
            toAutomata(regex.left, templayerLeft);
        }
        if (regex.right != null){
            int templayerRight = layer;
            templayerRight++;
            toAutomata(regex.right, templayerRight);
        }
    }

    public Automata parseAutomata(RegExp regex) {
        int layer = 0;
        toAutomata(regex, layer);
        return parseToOneAutomata();
    }

    private Automata parseToOneAutomata() {
        //create automata for result
        Automata<Integer> thompsonAutomata;

        //thompsonAutomata is first automata of the list
        Map.Entry<Automata, Integer> mapEntry = automatasPerLayer.entrySet().iterator().next();
        thompsonAutomata = mapEntry.getKey();

        int layer = mapEntry.getValue();
        int stateNumber = (int)mapEntry.getKey().getLastFromStates();

        //loop throught all the automatas in the list expect the first one
        while(automatasPerLayer.size() > 1) {
            //Create temporary list for the transistion with in the result automata
            List<Transition<Integer>> tempTransitions = new ArrayList<>();
            tempTransitions.addAll(thompsonAutomata.getAllTransitions());

            //create an Iterator for the temporary list
            Iterator<Transition<Integer>> itr = tempTransitions.iterator();
            List<Transition<Integer>> toRemoveList = new ArrayList<>();

            //loop throught all the transitions
            while(itr.hasNext()){
                Transition<Integer> t = itr.next();
                if(t.getSymbol() == Transition.OTHERAUTOMATA){ //if a transition is the OTHERAUTOMATA symbol. Search for the correct automata and paste it within the result
                    //save start and end state to connect the other automata correct
                    int startState = t.getFromState();
                    int endState = t.getToState();

                    //loop throught all earlier created automatas and find the first one with a higher layer number
                    for( Map.Entry<Automata, Integer> entry : automatasPerLayer.entrySet()){
                        if(layer+1 == entry.getValue()){
                            //copy the alphabet of the automata
                            Iterator<Character> alphabetITR = entry.getKey().getAlphabet().iterator();
                            while(alphabetITR.hasNext()){
                                thompsonAutomata.addAlphabetCharacter(alphabetITR.next());
                            }

                            //add the connection transition
                            int firstNumber = stateNumber + 1;
                            thompsonAutomata.addTransition(new Transition<Integer> (startState, Transition.EPSILON, firstNumber));

                            //copy the transitions
                            List<Transition<Integer>> tempTransitionsList = new ArrayList<>();
                            tempTransitionsList.addAll(entry.getKey().getAllTransitions());
                            Iterator<Transition<Integer>> transitionIterator = tempTransitionsList.iterator();
                            while(transitionIterator.hasNext()) {
                                Transition<Integer> trans = transitionIterator.next();
                                thompsonAutomata.addTransition(new Transition<Integer>(stateNumber + trans.getFromState(), trans.getSymbol(), stateNumber + trans.getToState()));
                            }

                            //add the end connection transition with the result automata
                            thompsonAutomata.addTransition(new Transition<Integer> ((Integer)entry.getKey().getFinalState() + stateNumber, Transition.EPSILON, endState));

                            //increment stateNumber
                            stateNumber = stateNumber + (Integer)entry.getKey().getFinalState();

                            //remove used automata from the automata's list
                            automatasPerLayer.remove(entry.getKey());
                            break;
                        }
                    }
                    //remove the transition with otherautomata symbol from the transitionlist
                    toRemoveList.add(t);
                    itr.remove();
                }
            }
            //remove the transitions which have to be removed after the iterator
            thompsonAutomata.getAllTransitions().removeAll(toRemoveList);
            //increment the layer and loop for the next time to fill in all otherautomata symbols
            layer++;
        }

        //return one complete automata with the thompson construction
        return thompsonAutomata;
    }

    private Automata operatorToAutomata(RegExp regex) {
        switch (regex.operator) {
            case ONE:
                return one(regex);
            case OR:
                return or(regex);
            case DOT:
                return dot(regex);
            case STAR:
                return star(regex);
            case PLUS:
                return plus(regex);
            default:
                break;
        }

        return null;
    }

    private Automata one(RegExp regex) {
        Automata<Integer> automata = new Automata<>();

        for (char symbol : regex.terminals.toCharArray()) {
            automata.addAlphabetCharacter(symbol);
        }

        automata.addTransition( new Transition<Integer> (1, regex.terminals.toCharArray()[0], 2) );

        automata.defineAsStartState(1);
        automata.defineAsFinalState(2);

        return automata;
    }

    private Automata or(RegExp regex) {
        Automata<Integer> automata = new Automata<>();

        for (char symbol : regex.terminals.toCharArray()) {
            automata.addAlphabetCharacter(symbol);
        }

        automata.addTransition( new Transition<Integer> (1, Transition.EPSILON, 2) );
        automata.addTransition( new Transition<Integer> (2, Transition.OTHERAUTOMATA, 3) );
        automata.addTransition( new Transition<Integer> (3, Transition.EPSILON, 6) );
        automata.addTransition( new Transition<Integer> (1, Transition.EPSILON, 4) );
        automata.addTransition( new Transition<Integer> (4, Transition.OTHERAUTOMATA, 5) );
        automata.addTransition( new Transition<Integer> (5, Transition.EPSILON, 6) );

        automata.defineAsStartState(1);
        automata.defineAsFinalState(6);

        return automata;
    }

    private Automata dot(RegExp regex) {
        Automata<Integer> automata = new Automata<>();

        for (char symbol : regex.terminals.toCharArray()) {
            automata.addAlphabetCharacter(symbol);
        }

        automata.addTransition( new Transition<Integer> (1, Transition.OTHERAUTOMATA, 2) );
        automata.addTransition( new Transition<Integer> (2, Transition.OTHERAUTOMATA, 3) );

        automata.defineAsStartState(1);
        automata.defineAsFinalState(3);

        return automata;
    }

    private Automata star(RegExp regex) {
        Automata<Integer> automata = new Automata<>();

        for (char symbol : regex.terminals.toCharArray()) {
            automata.addAlphabetCharacter(symbol);
        }

        automata.addTransition( new Transition<Integer> (1, Transition.EPSILON, 2) );
        automata.addTransition( new Transition<Integer> (2, Transition.OTHERAUTOMATA, 3) );
        automata.addTransition( new Transition<Integer> (3, Transition.EPSILON, 4) );
        automata.addTransition( new Transition<Integer> (3, Transition.EPSILON, 2) );
        automata.addTransition( new Transition<Integer> (1, Transition.EPSILON, 4) );

        automata.defineAsStartState(1);
        automata.defineAsFinalState(4);

        return automata;
    }

    private Automata plus(RegExp regex) {
        Automata<Integer> automata = new Automata<>();

        for (char symbol : regex.terminals.toCharArray()) {
            automata.addAlphabetCharacter(symbol);
        }

        automata.addTransition( new Transition<Integer> (1, Transition.EPSILON, 2) );
        automata.addTransition( new Transition<Integer> (2, Transition.OTHERAUTOMATA, 3) );
        automata.addTransition( new Transition<Integer> (3, Transition.EPSILON, 4) );
        automata.addTransition( new Transition<Integer> (3, Transition.EPSILON, 2) );

        automata.defineAsStartState(1);
        automata.defineAsFinalState(4);

        return automata;
    }


}
