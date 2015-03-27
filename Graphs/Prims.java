package Graphs;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by ksb on 27-08-2014.
 */
//for only undirected graphs
//Checked on spoj
public class Prims
{
    class Edge
    {
        public Edge(int to, long weight, Edge next)
        {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
        int to;
        long weight;
        Edge next;
    }
    class Pair implements Comparable<Pair>
    {
        int i;
        long prior;

        public Pair(int i, long prior)
        {
            this.i = i;
            this.prior = prior;
        }

        public int compareTo(Pair o)
        {
            int cmp = Long.valueOf(prior).compareTo(o.prior);
            if(cmp == 0)
            {
                cmp = Integer.valueOf(i).compareTo(o.i);
            }
            return cmp;
        }
    }
    Edge G[];
    int n;
    public Prims(int n)
    {
        this.n = n;
        G = new Edge[n];
    }

    public void addEdge(int a, int b, long c)
    {
        G[a] = new Edge (b, c, G[a]);
        G[b] = new Edge (a, c, G[b]);
    }

    public long mstCost()
    {
        //take 0 as start
        int s = 0;
        long dist[] = new long[n];
        boolean done[] = new boolean[n];
        Arrays.fill (done, false);

        long INF = Long.MAX_VALUE >> 1;
        Arrays.fill (dist, INF);
        dist[s] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<Pair> ();
        pq.add (new Pair (s, 0));
        long mstCost = 0;
        while (!pq.isEmpty ())
        {
            Pair p = pq.poll ();
            if (p.prior != dist[p.i])
            {
                continue;
            }
            mstCost += dist[p.i];
            done[p.i] = true;
            for (Edge e = G[p.i]; e != null; e = e.next)
            {
                if (done[e.to])
                {
                    continue;
                }
                if(e.weight < dist[e.to])
                {
                    dist[e.to] = e.weight;
                    pq.add (new Pair (e.to, e.weight));
                }
            }
        }
        return mstCost;
    }

}
