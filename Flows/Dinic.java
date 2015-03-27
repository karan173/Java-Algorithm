package Flows;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 23/11/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
/*
tested
 */
public class Dinic
{
    /*
    complexity - n*m*m
    for unit capacity graphs - O(m * sqrt(n))
     */
    public long INF = Long.MAX_VALUE >> 1;
    public long maxFlow = 0;
    int s,t;
    int n;
    public ArrayList<DiEdge> G[];
    int d[]; //distance label from s
    int ptr[];
    public Dinic(int n)
    {
        this.n = n;
        G = new ArrayList[n];
        for (int i = 0; i < n; i++)
        {
            G[i] = new ArrayList<DiEdge> ();
        }
    }

    public DiEdge addEdge(int from, int to, long cap)
    {
        DiEdge e = new DiEdge (from, to, cap);
        G[from].add (e);
        G[to].add (e);
        return e;
    }

    public long maxFlow(int s, int t, boolean reInitialize)
    {
        if (reInitialize)
        {
            maxFlow = 0;
            for (int i = 0; i < n; i++)
            {
                for (DiEdge e : G[i])
                {
                    e.flow = 0;
                }
            }
        }
        this.s = s;
        this.t = t;
        while (dinic_bfs ())    //create layering/level graph
        {
            ptr = new int[n];
            while (true)
            {
                long delFlow = dinic_dfs (s, INF);
                if (delFlow == 0)  //we have obtained a blocking flow
                {
                    break;
                }
                maxFlow += delFlow;
//                if (maxFlow >= 2)
//                {
//                    break;
//                }
            }
        }
        return maxFlow;
    }

    /*
    get flow from u to t in layer graph of residual graph
     */
    private long dinic_dfs(int u, long bottleNeckFlow)
    {
        if (u == t)
        {
            return bottleNeckFlow;
        }
        for (; ptr[u] < G[u].size (); ptr[u]++)
        {
            DiEdge e = G[u].get (ptr[u]);
            int v = e.other (u);
            if (d[v] == d[u] + 1)  //part of layer graph
            {
                long res =  e.getResidualCapacityTo (v);
                if (res == 0)
                {
                    continue;
                }
                long df = dinic_dfs (v, Math.min (bottleNeckFlow, res));
                if (df == 0)
                {
                    continue;
                }
                e.addFlowTo (v, df);
                return df;
            }
        }
        return 0; //no flow from u -> this vertex is blocked
    }

    /*
    create layer graph for the residual graph - i.e create distance labels using bfs
     */
    private boolean dinic_bfs()
    {
        d = new int[n];
        Arrays.fill (d, -1);
        Queue<Integer> queue = new ArrayDeque<Integer> ();
        queue.add (s);
        d[s] = 0;
        while (!queue.isEmpty ())
        {
            int u = queue.poll ();
            for (DiEdge e : G[u])
            {
                int v = e.other (u);
                if (d[v] < 0 && e.getResidualCapacityTo (v) > 0)
                {
                    d[v] = d[u] + 1;
                    queue.add (v);
                }
            }
        }
        return d[t] != -1;
    }
}
