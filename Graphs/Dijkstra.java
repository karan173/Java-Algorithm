package Graphs;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 4/10/13
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */

/*
0 based index
Tested
 */
public class Dijkstra
{
    int n;
    Edge G[];
    public static final long INF = Long.MAX_VALUE>>1;
    boolean undirected;
    public Dijkstra(int n, boolean undirected)
    {
        this.n = n;
        G = new Edge[n];
        this.undirected = undirected;
    }

    public void addEdge(int a, int b, long w)
    {
        G[a] = new Edge(b, w, G[a]);
        if(undirected)
            G[b] = new Edge(a, w, G[b]);
    }
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
    public long[] dijkstra(int s)
    {
        long dist[] = new long[n];
        Arrays.fill (dist, INF);
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
        pq.add(new Pair(s, 0));
        dist[s] = 0;
        while(!pq.isEmpty())
        {
            Pair p = pq.poll();
            if(p.prior >= INF)
            {
                break;
            }
            if(p.prior!=dist[p.i])
            {
                continue;
            }
            int u = p.i;
            for(Edge e = G[u]; e!=null; e=e.next)
            {
                int v = e.to;
                long newdist = e.weight + dist[u];
                if(newdist < dist[v])
                {
                    dist[v] = newdist;
                    pq.add(new Pair(v, newdist));
                }
            }
        }
        return dist;
    }
}