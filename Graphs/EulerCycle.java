package Graphs;

import java.util.ArrayList;
import java.util.Stack;

/*
1) undirected has euler cycle if all of even degree and graph connected
2) undirected has euler path if graph connected and either 0 or 2 vertices of odd degree
3) directed has euler cycle iff
    - outdegree = indegree for each
    - connected
4) directed has euler path iff
    - connected
    - 1 source 1 sink rest outdeg=indeg
    - all outdeg = indeg

algo
tour = stack
eulerTour(i)
    for j adj to i :
        delete_edge(i,j)
        eulerTour(j)
    tour.push(i)


 */

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 17/10/13
 * Time: 12:39 AM
 * To change this template use File | Settings | File Templates.
 */

/*
be careful, use Increased Stack size
 */
public class EulerCycle
{
    public Stack<Integer> eulerTour = new Stack<Integer> ();
    ArrayList<Integer> G[];
    int edgeCtr[];
    /*
    be careful, use Increased Stack size
     */
    public boolean getEulerTourDirected(int indeg[], int outDeg[], ArrayList<Integer> G[], int m)
    {
        this.G = G;
        int n = indeg.length;
        edgeCtr = new int[n];
        int numSource = 0, numSink = 0, numOther = 0, source = -1;
        for (int i = 0; i < n; i++)
        {
            switch (outDeg[i] - indeg[i])
            {
                case 0:
                    break;
                case 1:
                    source = i;
                    numSource++;
                    break;
                case -1:
                    numSink++;
                    break;
                default:
                    numOther++;
            }
        }

        if (numSink == 1 && numSource == 1 && numOther == 0)
        {
            dfs (source);
        }
        else if (numOther == 0 && numSink == 0 && numSource == 0)
        {
            for (int i = 0; i < n; i++)
            {
                if (outDeg[i] > 0)
                {
                    dfs (i);
                    break;
                }
            }
        }
        else
        {
            return false;
        }
        return eulerTour.size () == m + 1;
    }

    private void dfs(int u)
    {
        //traverse edges of u
        while (edgeCtr[u] < G[u].size ())
        {
            dfs (G[u].get (edgeCtr[u]++));
        }
        eulerTour.push (u);
    }
}
