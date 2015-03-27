package Flows;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 19/10/13
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */
/*
tested on tc srm 371 div1 lvl2
Note it is equivalent to max weighted bipartite matching, just multiply edge costs by -1
 */
public class AssignmentBipartite
{
    int[][] cost;
    int cap[][];
    int n,m, nv;
    MinCostMaxFlow mcmf;
    List<MinCostMaxFlow.Edge>[] graph;
    public long[] solve(int c[][])
    {
        n = c.length;
        m = c[0].length;
        nv = n+m+2;
        cost = new int[nv][nv];
        cap = new int[nv][nv];
        mcmf = new MinCostMaxFlow (nv);
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                addEdge (transformRow(i), transformCol(j), 1, c[i][j]);
            }
        }
        for (int i = 0; i < n; i++)
        {
            addEdge (0, transformRow (i), 1, 0);
        }
        for (int j = 0; j < m; j++)
        {
            addEdge (transformCol (j), nv-1, 1, 0);
        }
//        System.out.println ("xxx");
//        for (int i = 0; i < nv; i++)
//        {
//            for (MinCostMaxFlow.Edge e : mcmf.G[i])
//            {
//                if (e.from != i || e.getResidualCapacityTo (e.other (i))==0)
//                {
//                    continue;
//                }
//                System.out.println (e);
//            }
//        }
        mcmf.solve (0, nv - 1, Integer.MAX_VALUE, true);
//        for (int i = 0; i < nv; i++)
//        {
//            for (MinCostMaxFlow.Edge e : mcmf.G[i])
//            {
//                if (e. != i || e.getResidualCapacityTo (e.other (i))!=0)
//                {
//                    continue;
//                }
//                System.out.println (e);
//            }
//        }
        return new long[]{mcmf.netCost, mcmf.netFlow};
    }

    private void addEdge(int i, int j, int capacity, int cst)
    {
        mcmf.addEdge (i, j, capacity, cst);
    }

    private int transformCol(int j)
    {
        return n + j + 1;
    }

    private int transformRow(int i)
    {
        return i+1;
    }
}
