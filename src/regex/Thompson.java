package regex;

import automata.Automata;
import automata.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Thompson {
    private Stack<Automata> automataStack = new Stack<>();

    public Thompson() {
    }

    private List<Automata> toAutomata(RegExp regex) {
        List<Automata> automatas = new ArrayList<>();

        automataStack.push(operatorToAutomata(regex));

        if (regex.left != null)
        if (regex.right != null)

        return automatas;
    }

    public Automata parseAutomata(RegExp regex) {
        List<Automata> automatas = toAutomata(regex);
        return parseToOneAutomata(automatas);
    }

    private Automata parseToOneAutomata(List<Automata> automatas) {
        //voor iedere automaat in lijst
        // voeg tot 1 toe.

        return automatas.get(0);
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
        return automata;
    }

    private Automata or(RegExp regex) {
        return null;
    }

    private Automata dot(RegExp regex) {

        return null;
    }

    private Automata star(RegExp regex) {
        return null;

    }

    private Automata plus(RegExp regex) {

        return null;
    }


}
