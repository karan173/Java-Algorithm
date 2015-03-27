package dataStructures;

import Graphs.TreeGraph;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 9/8/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
/*
reduce LCA -> RMQ O(n)
preprocess RMQ O(nlogn) using sparse table
finally answer queries in O(1)
hence net <O(nlogn), O(1)>
 */

/*
tested at
http://codeforces.com/contest/191/problem/C
 */

public class StaticLCA
{
    int eulerTour[];
    int levels[];
    int firstOccurence[];     //also used as visited array
    int n;
    int tourCtr;
    TreeGraph treeGraph;
    StaticRMQ rmq;
    public int dist[];
    /*
    O(nlogn) preprocessing
     */
    public StaticLCA(TreeGraph treeGraph, int root)
    {
        this.treeGraph = treeGraph;
        n = treeGraph.tree.length;
        eulerTour = new int[2*n-1];
        levels = new int[2*n-1];
        firstOccurence = new int[n];
        Arrays.fill (firstOccurence, -1);
        tourCtr = 0;
        firstOccurence[root] = 0;

        dist = new int[n]; //only for this problem
        dist[root] = 0;
        dfs (root, 0);
//        System.out.println (Arrays.toString (eulerTour));
//        System.out.println (Arrays.toString (levels));
//        System.out.println (Arrays.toString (firstOccurence));
        rmq = new StaticRMQ (levels);
        this.treeGraph = null;
        levels = null;
    }

    /*
    returns index 0 based of lca in O(1)
    u and v should be 0 based
     */
    public int lcaQuery(int u, int v)
    {
        int idx1 = firstOccurence[u];
        int idx2 = firstOccurence[v];
        int eulerIdx = -1;
        if (idx1 < idx2)
        {
            eulerIdx = rmq.getMinIndex (idx1, idx2);
        }
        else
        {
            eulerIdx = rmq.getMinIndex (idx2, idx1);
        }
        return eulerTour[eulerIdx];
    }
    private void dfs(int i, int lvl)
    {
//        System.out.println (i);
        TreeGraph.TreeNode u = treeGraph.tree[i];
        firstOccurence[i] = tourCtr;
        levels[tourCtr] = lvl;
        eulerTour[tourCtr++] = i;
        for (int j : u.edges)
        {
            TreeGraph.TreeNode v = treeGraph.tree[j];
            if (firstOccurence[j] == -1)
            {
//                dist[j] = dist[i] + u.idx;
                dfs (j, lvl + 1);
//                System.out.println (i);
                levels[tourCtr] = lvl;
                eulerTour[tourCtr++] = i;
            }
        }
    }
}
