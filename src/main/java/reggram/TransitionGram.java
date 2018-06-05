package reggram;

public class TransitionGram<T extends Comparable> implements Comparable<TransitionGram<T>> {

    private T fromState;
    private char symbol;
    private T toState;

    public TransitionGram(T fromOrTo, char s)
    {
        this (fromOrTo, s, null);
    }

    public TransitionGram(T from, char s, T to)
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
        else if ( other instanceof TransitionGram )
        {
            return this.fromState.equals (((TransitionGram )other ).fromState) && this.toState.equals (((TransitionGram )other ).toState) && this.symbol == (((TransitionGram )other ).symbol);
        }
        else return false;
    }

    @SuppressWarnings("unchecked")
    public int compareTo(TransitionGram<T> t)
    {
        int fromCmp = fromState.compareTo(t.fromState);
        int symbolCmp = new Character (symbol).compareTo(new Character (t.symbol));
        int toCmp = 1;
        if(toState != null && t.toState != null) {
            toCmp = toState.compareTo(t.toState);
        }

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
        String result = this.getSymbol() + "";
        if(getToState() != null)
            result += getToState();
        return result;
    }

}
