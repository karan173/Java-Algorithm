package Graphs;

import dataStructures.UnionFind;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 14/10/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
/*
for undirected graphs
 */
public class Kruskal
{

    //static bcoz independent of outer class
    public static class Edge implements Comparable<Edge>
    {
        public int from, to; //0 based
        public long weight;

        public Edge(int from, int to, long weight)
        {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int other(int u)
        {
            if (u == from)
            {
                return to;
            }
            return from;
        }
        @Override
        public int compareTo(Edge o)
        {
            return Long.valueOf (weight).compareTo (o.weight);
        }
    }


    public ArrayList<Edge> MST;

    /*
    mlogn
     */
    public long mstCost(ArrayList<Edge> G, int n)
    {
        MST = new ArrayList<Edge> (n - 1);
        long cost = 0;
        UnionFind UF = new UnionFind (n + 1);
        Collections.sort (G);
        for (Edge e : G)
        {
            if (UF.isConnected (e.from, e.to))
            {
                continue;
            }
            UF.union (e.from, e.to);
            MST.add (e);
            cost += e.weight;
            if (MST.size () == n - 1)
            {
                break;
            }
        }
        return cost;
    }
}
