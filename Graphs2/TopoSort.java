package Graphs2;

import java.util.ArrayList;

/*
Tested
 */
public class TopoSort
{
    boolean done[];
    int n;
    ArrayList<Integer> adjList[];
    public int topoOrder[];
    int revCtr;
    /*
    returns reversed post order of graph
    graph should be dag for order to be topologically sorted
     */
    public int[] topoSort(ArrayList<Integer> adjList[])
    {
        this.adjList = adjList;
        n = adjList.length;
        done = new boolean[n];
        topoOrder = new int[n];
        revCtr = n-1;
        for (int i = 0; i < n; i++)
        {
            if (!done[i])
            {
                done[i] = true;
                dfs (i);
            }
        }
        return topoOrder;
    }

    private void dfs(int i)
    {
        for (int v : adjList[i])
        {
            if(!done[v])
            {
                done[v] = true;
                dfs (v);
            }
        }
        topoOrder[revCtr--] = i;
    }
}
