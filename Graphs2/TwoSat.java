package Graphs2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 4/10/13
 * Time: 3:21 AM
 * To change this template use File | Settings | File Templates.
 */
/*
Tested
 */
public class TwoSat
{
    /*
    see explanation in techniques

    we solve CNF form (a+b).(b+c).(a+c) etc
    suppose we have n variables and m conditions
    Input is an array of clauses x[i] + y[i]
    also x[i] in [0,n) means normal, in [n,2n) means negation of one in [0,n)   ==> 0 based index
    we have to find an assignment for the And of these clauses(CNF)
    op is a truth assignment or null if not possible to satisfy
    Complexity = O(n+m)
     */
    ArrayList<Integer> G[], revG[];
    int n, m;
    public boolean[] findTruthAss(int[] x, int[] y, int n)
    {
        this.n = n;
        m = x.length;
        G = new ArrayList[2*n];
        revG = new ArrayList[2*n];
        boolean assignment[] = new boolean[n];
        for (int i = 0; i < 2*n; i++)
        {
            G[i] = new ArrayList<Integer> ();
            revG[i] = new ArrayList<Integer> ();
        }
        //Build Implication Graph
        for (int i = 0; i < m; i++)
        {
            int negX = negate (x[i]);
            int negY = negate (y[i]);
            //x+y == !x=>y == !y=>x
            G[negX].add (y[i]);
            G[negY].add (x[i]);
            revG[y[i]].add (negX);
            revG[x[i]].add (negY);
        }
        SCC scc = new SCC ();
        scc.getSCC (G, revG);
        int comp[] = scc.comp;
        for (int i = 0; i < n; i++)
        {
            int neg = negate (i);
            if (comp[i] == comp[neg])
            {
                return null;
            }
            if (comp[i] > comp[neg])
            {
                 assignment[i] = true;
            }
            else
            {
                assignment[i] = false;
            }
        }
        return assignment;
    }

    private int negate(int x)
    {
        if (x >= n)
        {
            return x - n;
        }
        return x + n;
    }
}
