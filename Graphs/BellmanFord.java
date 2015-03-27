package Graphs;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 17/10/13
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
/*
Not tested much
 */
public class BellmanFord
{
    /*
    To find a -ve cycle in graph(reachable from any vertex) add a dummy vertex s connected to each vertex with cost 0  and do
    bellman ford from s
    or use floyd warshall O(n^3)
     */
    public static class InEdge
    {
        int from;
        long cost;
        public InEdge(int from, long cost)
        {
            this.from = from;
            this.cost = cost;
        }
    }
    /*
    O(mn) works for both directed and undirected
    ans in minDist
    returns false if graph contains -ve cycle
    s is 0 based
    memory = O(n)
     */
    public long minDist[];
    int pred[];
    int lastChanged = -1;
    int n;
    public boolean getShortestPaths(ArrayList<InEdge> G[], int s)
    {
        n = G.length;
        long dp[][] = new long[n + 1][];
        pred = new int[n];
        //dp[i][j] = shortest path to j from s using <= i edges

        //intialization
        dp[0] = new long[n];
        Arrays.fill (dp[0], Integer.MAX_VALUE);
        Arrays.fill (pred, -1);
        dp[0][s] = 0;
        boolean changed = false;
        for (int i = 1; i <= n; i++)
        {
            changed = false;
            dp[i] = new long[n];
            for (int j = 0; j < n; j++)
            {
                dp[i][j] = dp[i - 1][j];
                for (InEdge e : G[j])
                {
                    long newVal = e.cost + dp[i - 1][e.from];
                    if (newVal < dp[i][j])
                    {
                        dp[i][j] = newVal;
                        changed = true;
                        pred[j] = e.from;
                        lastChanged = j;
                    }
                }
            }
            dp[i - 1] = null;
            if (!changed) //done - no more changes
            {
                dp[n] = dp[i];
                break;
            }
        }

        //-ve cycle, coz changed = true implies we exited normally, and in last iteration, some value changed hence -ve cycle
        if (changed)
        {
            return false;
        }
        minDist = dp[n];
        return true;
    }

    /*
    must be called after getShortestPath returns true
    returns shortest s-t path
     */
    public Stack<Integer> getShortestPathActual(int t)
    {
        Stack<Integer> path = new Stack<Integer> ();
        do
        {
            path.push (t);
            t = pred[t];
        } while (t != -1);
        return path;
    }

    /*
    we must have called getShortestPaths and it should have returned false
    both start and end vertex are added to cycle
    eg of form abcda
    also cycle is in reverse order [note has order since directed]
    hence removeLast or poll to get actual cycle
    will throw Exception if no -ve cycle
    Also note this code only finds -ve cycle reachable from s
    To find any -ve cycle which may/may not be reachable from S we must modify Bellman ford by adding a dummy vertex,
    connected to each vertex with weights 0, and then do bellman ford from it.
     */
    public Deque<Integer> getNegativeCycleReachableFromS()
    {
        boolean visited[] = new boolean[n];
        Arrays.fill (visited, false);
        Deque<Integer> negateiveCycle = new ArrayDeque<Integer> ();
        int t = lastChanged;   //t is part of a negative cycle
        do
        {
            negateiveCycle.push (t);
            visited[t] = true;
            t = pred[t];
        } while (!visited[t]);

        negateiveCycle.push (t); //cycle completed
        //remove starting vertices not part of chain
        while (negateiveCycle.peekFirst () != t)
        {
            negateiveCycle.removeFirst ();
        }
        return negateiveCycle;
    }
}
