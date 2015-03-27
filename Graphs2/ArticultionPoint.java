package Graphs2;

import java.util.ArrayList;

/**
 * Created by ksb on 29-06-2014.
 */
//Tested on Critical Links and Kingcon
public class ArticultionPoint
{
    /*
    A vertex u is an art point if and only if
        -it is the root and has >= 2 children[there wont be any cross edges b/w subtrees of root, since if
            they were linked via cross edges, they should have belonged to the same subtree
        -it has a vertex v in its subtree such that there is no path from subtree rooted at v to the nodes above u

    define low[v] = the min discovery time of a node which can be reached from subtree rooted at v using at max 1 back
    edge

    u is an art point if there exists a v such that (u->v) is a tree edge and low[v] >= discovery time of u

    Bridge, -> first note that only tree edges can be bridges
    Tree edge u->v is a bridge if low[v] >= time[u]
     */
    public static class Edge
    {
        public int from, to;

        Edge(int from, int to)
        {
            this.from = from;
            this.to = to;
        }
    }

    public boolean articulationPoints[];
    public ArrayList<Edge> bridges;
    ArrayList<Integer> G[];
    int curTime;
    int time[];
    int low[];
    int n;

    private void initialize(ArrayList<Integer> G[])
    {
        this.G = G;
        n = G.length;
        curTime = 0;
        time = new int[n]; //0
        low = new int[n];
        articulationPoints = new boolean[n];
        bridges = new ArrayList<Edge> ();
    }
    public void func(ArrayList<Integer> G[])
    {
        initialize (G);
        for (int i = 0; i < n; i++)   //graph may be a forest
        {
            if(time[i] == 0)
            {
                dfs (i, -1);
            }
        }
    }

    private void dfs(int u, int par)
    {
        time[u] = ++curTime;
        low[u] = curTime;
        int numChildren = 0;
        for(int v : G[u])
        {
            if(time[v] == 0) //not visited
            {
                numChildren++;
                //[u,v] is a tree edge
                dfs (v, u);
                if(low[v] >= time[u])
                {
                    //root or articulation point
                    if(par!=-1 || numChildren > 1)    //second case is for root
                        articulationPoints[u] = true;
                }
                if(low[v] > time[u]) //low[v] == v
                {
                    bridges.add (new Edge (u, v));
                }
                low[u] = Math.min (low[v], low[u]);
            }
            else if(v != par) //for undirected graphss
            {
                //u,v is a back edge
                //It is also possible that v,u was a back edge hence v is visited, but then v must be
                //in subtree of child of u, hence already included in low of unvisited children of u
                low[u] = Math.min (low[u], time[v]);
            }
        }

    }

    public static void main(String[] args)
    {

    }

}
