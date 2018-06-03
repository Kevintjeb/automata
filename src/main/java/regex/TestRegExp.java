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

    public RegExp regexEqual1(){
        //leftside of dot
        RegExp a = new RegExp("a");
        RegExp starA = a.star();

        RegExp b = new RegExp("b");
        RegExp starB = b.star();

        RegExp starADOTStarB = starA.dot(starB);
        RegExp plusleft =  starADOTStarB.plus();

        //rightside of dot
        //leftside of OR
        RegExp b2 = new RegExp("b");
        RegExp b2Star = b2.star();

        RegExp b3 = new RegExp("b");
        RegExp b2Starb3 = b2Star.dot(b3);

        RegExp b4 = new RegExp("b");
        RegExp b4DOTb2Starb3 = b4.dot(b2Starb3);

        //rightside of OR
        RegExp a2 = new RegExp("a");
        RegExp a3 = new RegExp("a");

        RegExp a2DOTa3 = a2.dot(a3);
        RegExp b5 = new RegExp("b");
        RegExp b5DOTa2DOTa3 = b5.dot(a2DOTa3);

        RegExp b6 = new RegExp("b");
        RegExp starB6 = b6.star();

        RegExp starb6DOTb5enz = starB6.dot(b5DOTa2DOTa3);

        RegExp a4 = new RegExp("a");
        RegExp a4DOTenz = a4.dot(starb6DOTb5enz);

        RegExp or = b4DOTb2Starb3.or(a4DOTenz);

        RegExp plusright = or.plus();

        RegExp MainDOT = plusleft.dot(plusright);

        return MainDOT;

    }

    public RegExp regexEqual2(){
        //left of dot
        RegExp a = new RegExp("a");
        RegExp b = new RegExp("b");
        RegExp aORb = a.or(b);
        RegExp leftStar = aORb.star();

        //right of DOT
        //left of Dot
        //left of OR
        RegExp b2 = new RegExp("b");
        RegExp b2plus = b2.plus();
        RegExp b3 = new RegExp("b");
        RegExp b2plusDotb3 = b2plus.dot(b3);

        //right of OR
        RegExp a2 = new RegExp("a");
        RegExp a3 = new RegExp("a");
        RegExp a2DOTa3 = a2.dot(a3);
        RegExp b4 = new RegExp("b");
        RegExp b4DOTa2DOTa3 = b4.dot(a2DOTa3);

        RegExp b5 = new RegExp("b");
        RegExp b5Star = b5.star();

        RegExp b5StarDOTb4DOTa2DOTa3 = b5Star.dot(b4DOTa2DOTa3);
        RegExp a4 = new RegExp("a");
        RegExp a4DOTb5St = a4.dot(b5StarDOTb4DOTa2DOTa3);

        RegExp OR = b2plusDotb3.or(a4DOTb5St);

        //right of DOT
        RegExp a5 = new RegExp("a");
        RegExp a6 = new RegExp("a");
        RegExp a5DOTa6 = a5.dot(a6);

        RegExp b6 = new RegExp("b");
        RegExp b6Plus = b6.plus();
        RegExp b6plusDOTa5DOTa6 = b6Plus.dot(a5DOTa6);

        RegExp a7 = new RegExp("a");
        RegExp a7Dotb6p = a7.dot(b6plusDOTa5DOTa6);

        RegExp b7 = new RegExp("b");
        RegExp b7plus = b7.plus();
        RegExp b8 = new RegExp("b");
        RegExp b8DOTb7plus = b8.dot(b7plus);

        RegExp OR2 = a7Dotb6p.or(b8DOTb7plus);
        RegExp starOR2 = OR2.star();

        RegExp dot = OR.dot(starOR2);

        RegExp mainDOT = leftStar.dot(dot);

        return mainDOT;
    }
}
