package regex;

/**
 * Write a description of class TestRegExp here.
 *
 *
 * @author Kevin & Rick
 * @version 1.0
*/

public class TestRegExp
{
    private RegExp expr1, expr2, expr3, expr4, expr5, a, b, all;

    public TestRegExp()
    {
        a = new RegExp("a");
        b = new RegExp("b");
        
        // expr1: "baa"
        expr1 = new RegExp("baa");
        // expr2: "bb"
        expr2 = new RegExp("bb");
        // expr3: "baa | baa"
        expr3 = expr1.or(expr2);
        
        // all: "(a|b)*"
        all = (a.or(b)).star();
        
        // expr4: "(baa | baa)+"
        expr4 = expr3.plus();
        // expr5: "(baa | baa)+ (a|b)*"
        expr5 = expr4.dot(all);
    }
    
    public void testLanguage()
    {
        System.out.println("taal van (baa):\n" + expr1.getLanguage(5));
        System.out.println("taal van (bb):\n" + expr2.getLanguage(5));
        System.out.println("taal van (baa | bb):\n" + expr3.getLanguage(5));

        System.out.println("taal van (a|b)*:\n" + all.getLanguage(5));
        System.out.println("taal van (baa | bb)+:\n" + expr4.getLanguage(5));
        System.out.println("taal van (baa | bb)+ (a|b)*:\n" + expr5.getLanguage(6));
    }

    public RegExp testThompson(){
        RegExp a = new RegExp("a");
        RegExp b = new RegExp("b");
        RegExp c = new RegExp("c");
        RegExp c2 = new RegExp("c");

        RegExp bStar = b.star();

        RegExp aPlus = a.plus();

        RegExp bStartA = bStar.dot(a);

        RegExp aPlusB = aPlus.dot(b);

        RegExp or = bStartA.or(aPlusB);

        RegExp orStar = or.star();

        RegExp orStarC = orStar.dot(c);

        RegExp beginRegex = c2.dot(orStarC);

        return beginRegex;


    }

    public RegExp testThompson2(){
        RegExp a = new RegExp("a");
        RegExp a2 = new RegExp("a");
        RegExp b = new RegExp("b");

        RegExp dot = a.dot(a2);

        RegExp star = dot.star();

        RegExp or = star.or(b);

        return or;
    }

    public RegExp testThompson3(){
        RegExp a = new RegExp("a");
        RegExp a2 = new RegExp("a");
        RegExp b = new RegExp("b");
        RegExp b2 = new RegExp("b");

        RegExp dot = b.dot(a);
        RegExp dot2 = b2.dot(a2);

        RegExp or = dot.or(dot2);

        return or;
    }

    public RegExp testThompson4(){
        RegExp a = new RegExp("a");
        RegExp a2 = new RegExp("a");
        RegExp b = new RegExp("b");
        RegExp b2 = new RegExp("b");
        RegExp b3 = new RegExp("b");

        RegExp or = a.or(b);
        RegExp star = or.star();


        RegExp dot1 = star.dot(a2);
        RegExp dot2 = dot1.dot(b2);
        RegExp dot3 = dot2.dot(b3);


        return dot3;
    }
}
