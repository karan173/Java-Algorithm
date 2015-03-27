package Interviews;

import InputOutput.FastReader;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 6/10/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeDiameter
{
    class Edge
    {
        int to;
        int cost;

        Edge(int to, int cost)
        {
            this.to = to;
            this.cost = cost;
        }
    }
    ArrayList<Edge>[] G;
    public void solve(int testNumber, FastReader in, PrintWriter out)
    {
        int n = in.ni ();
        maxCost = new int[n];
        diameter = new int[n];
        G = new ArrayList[n];
        for (int i = 0; i < n; i++)
        {
            G[i] = new ArrayList<Edge> ();
        }
        for (int i = 0; i < n - 1; i++)
        {
            int a = in.ni ()-1;
            int b = in.ni () - 1;
            int c = in.ni ();
            G[a].add (new Edge (b, c));
            G[b].add (new Edge (a, c));
        }
        dfs(0, -1);
        out.println (diameter[0]);
    }
    int diameter[];
    int maxCost[];
    private void dfs(int u, int parent)
    {
        int max = 0, secondMax = 0;
        for (Edge e : G[u])
        {
            int v = e.to;
            if (v == parent)
            {
                continue;
            }
            int cost = e.cost;
            dfs (v, u);
            diameter[u] = Math.max (diameter[u], diameter[v]);
            int maxInThisSubtree = cost + maxCost[v];
            if (maxInThisSubtree > max)
            {
                secondMax = max;
                max = maxInThisSubtree;
            }
            else if (maxInThisSubtree > secondMax)
            {
                secondMax = maxInThisSubtree;
            }
        }
        maxCost[u] = max;
        diameter[u] = Math.max (diameter[u], max + secondMax);
    }
}
