package reggram;

import automata.Automata;
import automata.Transition;

public class TestRegGram {
    static public RegGram<Integer> getRegGram1(){
        System.out.println("------------------- Test RegGram 1 ---------------------------");
        Character[] alphabet = {'a', 'b'};
        RegGram<Integer> regGram = new RegGram<>(alphabet);

        regGram.addTransition(new TransitionGram<Integer>(0, 'a', 1));
        regGram.addTransition(new TransitionGram<Integer>(0, 'b', 4));

        regGram.addTransition(new TransitionGram<Integer>(1, 'a', 4));
        regGram.addTransition(new TransitionGram<Integer>(1, 'b', 2));
        regGram.addTransition(new TransitionGram<Integer>(1, 'b'));

        regGram.addTransition(new TransitionGram<Integer>(2, 'a', 3));
        regGram.addTransition(new TransitionGram<Integer>(2, 'b', 4));
        regGram.addTransition(new TransitionGram<Integer>(2, 'a'));

        regGram.addTransition(new TransitionGram<Integer>(3, 'a', 1));
        regGram.addTransition(new TransitionGram<Integer>(3, 'b', 2));
        regGram.addTransition(new TransitionGram<Integer>(3, 'b'));

        regGram.addTransition(new TransitionGram<Integer>(4, 'a', 4));
        regGram.addTransition(new TransitionGram<Integer>(4, 'b', 4));


        regGram.defineAsStartState(0);

        System.out.println("As regular grammar");
        regGram.printInfo();

        Automata ndfa = regGram.toNDFA();

        System.out.println("As NDFA");
        ndfa.printInfo();

        RegGram regGram1 = ndfa.toRegGramm();

        System.out.println("Back as regular grammer");
        regGram1.printInfo();

        return regGram;
    }

    static public RegGram<Integer> getRegGram2(){
        System.out.println("------------------- Test RegGram 2 ---------------------------");
        Character[] alphabet = {'a', 'b'};
        RegGram<Integer> regGram = new RegGram<>(alphabet);

        regGram.addTransition(new TransitionGram<Integer>(0, 'a', 1));
        regGram.addTransition(new TransitionGram<Integer>(0, 'a', 2));
        regGram.addTransition(new TransitionGram<Integer>(0, Transition.EPSILON));

        regGram.addTransition(new TransitionGram<Integer>(1, 'b', 0));
        regGram.addTransition(new TransitionGram<Integer>(1, 'b'));

        regGram.addTransition(new TransitionGram<Integer>(2, 'a', 2));
        regGram.addTransition(new TransitionGram<Integer>(2, 'a', 3));

        regGram.addTransition(new TransitionGram<Integer>(3, 'a', 0));
        regGram.addTransition(new TransitionGram<Integer>(3, 'b', 1));
        regGram.addTransition(new TransitionGram<Integer>(3, 'a', 3));
        regGram.addTransition(new TransitionGram<Integer>(3, 'a'));


        regGram.defineAsStartState(0);

        System.out.println("As regular grammar");
        regGram.printInfo();

        Automata ndfa = regGram.toNDFA();

        System.out.println("As NDFA");
        ndfa.printInfo();

        RegGram regGram1 = ndfa.toRegGramm();

        System.out.println("Back as regular grammer");
        regGram1.printInfo();

        return regGram;
    }
}
