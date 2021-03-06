package regex;

import automata.Automata;

import java.util.*;

/**
 * Voorbeeld class voor het representeren van reguliere expressies
 *
 * @author Kevin & Rick
 * @version 1.0
 */
public class RegExp {
    Operator operator;
    String terminals;

    // De mogelijke operatoren voor een reguliere expressie (+, *, |, .)
    // Daarnaast ook een operator definitie voor 1 keer repeteren (default)
    public enum Operator {
        PLUS, STAR, OR, DOT, ONE, LEFTPARENTHESES, RIGHTPARENTHESES
    }

    RegExp left;
    RegExp right;

    static final Comparator<String> compareByLength
            = (s1, s2) -> {
        if (s1.length() == s2.length()) {
            return s1.compareTo(s2);
        } else {
            return s1.length() - s2.length();
        }
    };


    public RegExp() {
        operator = Operator.ONE;
        terminals = "";
        left = null;
        right = null;
    }

    public RegExp(String p) {
        operator = Operator.ONE;
        terminals = p;
        left = null;
        right = null;
    }

    public RegExp(char p) {
        operator = Operator.ONE;
        terminals = String.valueOf(p);
        left = null;
        right = null;
    }

    public RegExp plus() {
        RegExp result = new RegExp();
        result.operator = Operator.PLUS;
        result.left = this;
        return result;
    }

    public RegExp star() {
        RegExp result = new RegExp();
        result.operator = Operator.STAR;
        result.left = this;
        return result;
    }

    public RegExp or(RegExp e2) {
        RegExp result = new RegExp();
        result.operator = Operator.OR;
        result.left = this;
        result.right = e2;
        return result;
    }

    public RegExp dot(RegExp e2) {
        RegExp result = new RegExp();
        result.operator = Operator.DOT;
        result.left = this;
        result.right = e2;
        return result;
    }

    public SortedSet<String> getLanguage(int maxSteps) {
        SortedSet<String> emptyLanguage = new TreeSet<String>(compareByLength);
        SortedSet<String> languageResult = new TreeSet<String>(compareByLength);

        SortedSet<String> languageLeft, languageRight;

        if (maxSteps < 1) return emptyLanguage;

        switch (this.operator) {
            case ONE: {
                languageResult.add(terminals);
            }

            case OR:
                languageLeft = left == null ? emptyLanguage : left.getLanguage(maxSteps - 1);
                languageRight = right == null ? emptyLanguage : right.getLanguage(maxSteps - 1);
                languageResult.addAll(languageLeft);
                languageResult.addAll(languageRight);
                break;


            case DOT:
                languageLeft = left == null ? emptyLanguage : left.getLanguage(maxSteps - 1);
                languageRight = right == null ? emptyLanguage : right.getLanguage(maxSteps - 1);
                for (String s1 : languageLeft)
                    for (String s2 : languageRight) {
                        languageResult.add(s1 + s2);
                    }
                break;

            // STAR(*) en PLUS(+) kunnen we bijna op dezelfde manier uitwerken:
            case STAR:
            case PLUS:
                languageLeft = left == null ? emptyLanguage : left.getLanguage(maxSteps - 1);
                languageResult.addAll(languageLeft);
                for (int i = 1; i < maxSteps; i++) {
                    HashSet<String> languageTemp = new HashSet<String>(languageResult);
                    for (String s1 : languageLeft) {
                        for (String s2 : languageTemp) {
                            languageResult.add(s1 + s2);
                        }
                    }
                }
                if (this.operator == Operator.STAR) {
                    languageResult.add("");
                }
                break;


            default:
                System.out.println("getLanguage is nog niet gedefinieerd voor de operator: " + this.operator);
                break;
        }


        return languageResult;
    }

    public boolean equals(RegExp r2) {
        Thompson thompson = new Thompson();
        Thompson thompson2 = new Thompson();

        Automata a1 = thompson.parseAutomata(this);
        Automata a2 = thompson2.parseAutomata(r2);

        Automata dfa1 = a1.NDFAtoDFA();
        Automata dfa2 = a2.NDFAtoDFA();

        Automata optA1 = dfa1.brzozowski();
        Automata optA2 = dfa2.brzozowski();

        Automata NotOptA2 = optA2.denial();

        Automata a3 = optA1.and(NotOptA2);

        Automata result = a3.brzozowski();

        if (result.getFinalStates().size() == 0)
            return true;
        else
            return false;
    }

    public SortedSet getAcceptedWords() {
        Thompson thompson = new Thompson();
        Automata automata = thompson.parseAutomata(this).NDFAtoDFA();

        return this.getLanguage(automata.getStates().size());

    }


    public Operator getOperator() {
        return operator;
    }

}

