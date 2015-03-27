package dataStructures;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 17/8/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
/* checked on codeforces fools and roads and spoj LCA */
/*
HL decompostion partitions a tree into disjoint path graphs where each path is
composed of just heavy edges and linked via light edges
From any root to leaf path, there are just logn light edges
and hence logn chains/heavy subpaths
 */
public class HLDecomposition
{
    public int levels[]; //level of ith node
    public int size[]; //size of subtree of ith node including itself
    public int parent[]; //parent of ith node, parent of root is -1
    public int heavyChild[]; //heavy child of ith node, note each node can have at most one heavy child
    public Edge treeGraph[];  //original graph
    public int head[]; //highest node in chain of ith node
    public int cnt[]; //number of nodes in chain headed by ith node
    /*
    O(n)
     start is the node from which we want to start dfs-> for rooted trees
     */
    public HLDecomposition(Edge treeGraph[], int start)
    {
        int n = treeGraph.length;
        levels = new int[n];
        size = new int[n];
        parent = new int[n];
        heavyChild = new int[n];
        this.treeGraph = treeGraph;
        levels[start] = 0;
        parent[start] = -1;
        calculateLevelsSizeParents (start);
        head = new int[n];
        cnt = new int[n];
        calculateHeads (start, start);
    }

    /*
    invariant-> head for parent has been set
     */
    private void calculateHeads(int i, int curHead)
    {
        cnt[curHead]++;
        head[i] = curHead;
        for (Edge e = treeGraph[i]; e != null; e = e.next)
        {
            int j = e.to;
            if (j == parent[i])
            {
                continue;
            }
            if (heavyChild[i] == j)
            {
                calculateHeads (j, curHead);
            }
            else  //j is a light child, therefore heads a new chain
            {
                calculateHeads (j, j);
            }
        }
    }

    private boolean isLightChild(int i)
    {
        if (parent[i] == -1 || heavyChild[parent[i]] != i)
        {
            return true;
        }
        return false;
    }


    private void calculateLevelsSizeParents(int i)
    {
        size[i] = 1;
        heavyChild[i] = -1;
        int maxSizeChildIndex = -1;
        for (Edge e = treeGraph[i]; e != null; e = e.next)
        {
            int j = e.to;
            if (j != parent[i])
            {
                levels[j] = levels[i] + 1;
                parent[j] = i;
                calculateLevelsSizeParents (j);
                size[i] += size[j];
                if (maxSizeChildIndex == -1 || size[maxSizeChildIndex] < size[j])
                {
                    maxSizeChildIndex = j;
                }
            }
        }
        if (maxSizeChildIndex != -1 && 2 * size[maxSizeChildIndex] > size[i])
        {
            heavyChild[i] = maxSizeChildIndex;
        }
    }

    public int lca(int u, int v)
    {
        while (head[u] != head[v])  //while both not in same chain
        {
            if (levels[head[u]] > levels[head[v]])
            {
                u = parent[head[u]]; //advance u to next chain
            }
            else
            {
                v = parent[head[v]];
            }
        }
        //now in same chain, return higher node
        return ((levels[u] > levels[v]) ? v : u);
    }
}
