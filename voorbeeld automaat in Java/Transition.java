/**
 * The class Automata represents both DFA and NDFA: some NDFA's are also DFA
 * Using the method isDFA we can check this
 * 
 * We use '$' to denote the empty symbol epsilon
 * 
 * @author Paul de Mast
 * @version 1.0
 */

public class Transition<T extends Comparable> implements Comparable<Transition<T>>
{

    public static final char EPSILON = '$';
    
    private T fromState;
    private char symbol;
    private T toState;

   // this constructor can be used to define loops:
    public Transition(T fromOrTo, char s)
    {
        this (fromOrTo, s, fromOrTo);
    }

    public Transition(T from, T to)
    {
        this(from, EPSILON, to);
    }


    public Transition(T from, char s, T to)
    {
        this.fromState = from;
        this.symbol = s;
        this.toState = to;
    }

    
    // overriding equals
    public boolean equals ( Object other )
    {
       if ( other == null )
          {
          return false;
          }
       else if ( other instanceof Transition )
          {
               return this.fromState.equals (((Transition )other ).fromState) && this.toState.equals (((Transition )other ).toState) && this.symbol == (((Transition )other ).symbol);
          }
       else return false;
    }
    
    @SuppressWarnings("unchecked")
    public int compareTo(Transition<T> t) 
    {
        int fromCmp = fromState.compareTo(t.fromState);
        int symbolCmp = new Character (symbol).compareTo(new Character (t.symbol));
        int toCmp = toState.compareTo(t.toState);
        
        return (fromCmp != 0 ? fromCmp : (symbolCmp != 0 ? symbolCmp : toCmp));
    }

    public T getFromState()
    {
        return fromState;
    }

    public T getToState()
    {
        return toState;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public String toString()
    {
       return "(" + this.getFromState() + ", " + this.getSymbol() + ")" + "-->" +  this.getToState();
    }
    
}
