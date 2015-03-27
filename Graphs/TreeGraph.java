package Graphs;

import java.util.ArrayList;

public class TreeGraph
{
    public TreeNode tree[];
    public TreeGraph(int n)
    {
        tree = new TreeNode[n];
        for (int i = 0; i < n; i++)
        {
            tree[i] = new TreeNode (i + 1);
        }
    }

    public void addEdge(int u, int v)
    {
        tree[u].edges.add (v);
        tree[v].edges.add (u);
    }

    public class TreeNode
    {
//        public int idx;
        public ArrayList<Integer> edges;
        public TreeNode(int i)
        {
            edges = new ArrayList<Integer> ();
//            idx = 1;
        }
    }


}
