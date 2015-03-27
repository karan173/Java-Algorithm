

package Flows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 5/10/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
/*
Tested
Complexity = O(n*m*m)
 */
public class FordFulkerson
{
    /*
    For undirected simply call addedge twice
    change each undirected to 2 directed
     */
    public ArrayList<DiEdge> G[];
    public FordFulkerson(int n)
    {
        this.n = n;
        G = new ArrayList[n];
        for (int i = 0; i < n; i++)
        {
            G[i] = new ArrayList<DiEdge> ();
        }
    }
    /*
    0 based index
    NOTE WE ADD SAME COPY OF EDGE TO BOTH ADJLISTS SO THAT THEY SHARE THE SAME FLOW
    ALSO WE ADD TO ADJLIST OF B SO THAT WE CAN FOLLOW A BACKWARD EDGE

    NOTE SEND ARGUMENTS to addedge such that edge from a to b
     */
    public DiEdge addEdge(int from, int to, long cap)
    {
        DiEdge e = new DiEdge (from, to, cap);
//        System.out.println (e);
        G[from].add (e);
        G[to].add (e);
        return e;
    }
    /*
        Note edge from u to v
        but include in adjacency list of both
        coz we may follow a back edge
         */


    /*
    takes 0 based indices
    returns netflow
    <= NM/2 augmenting paths
    BFS takes time O(M)
    hence runtime = O(N*M^2)   also O(M*maxflow)
     */
    public long maxFlow = 0;
    public long maxFlow(int s, int t, boolean reInitialize)
    {
        if (reInitialize)
        {
            maxFlow = 0;
            for (int i = 0; i < n; i++)
            {
                for (DiEdge e : G[i])
                {
                    e.flow = 0;
                }
            }
        }
        this.s = s;
        this.t = t;
        //initialize all flows to 0
//        for (int i = 0; i < n; i++)
//        {
//            for (DiEdge e : G[i])
//            {
//                e.flow = 0;
//            }
//        }
        while (hasAugPathBFS ())   //while there's an augmenting path to t
        {
            long delFlow = getBottleNeckCapacity ();
            addFlows (delFlow);
            maxFlow += delFlow;
        }
//        for (int i = 0; i < G.length; i++)
//        {
//            System.out.println (G[i]);
//        }
        return maxFlow;
    }

    private long getBottleNeckCapacity()
    {
        long bottleNeckCap = Long.MAX_VALUE;
        for (int v = t; v != s; v = prevEdge[v].other (v))
        {
            long residualCap = prevEdge[v].getResidualCapacityTo (v);
            bottleNeckCap = Math.min (bottleNeckCap, residualCap);
        }
        return bottleNeckCap;
    }

    private void addFlows(long delFlow)
    {
        //work backwords from t
        for(int v = t; v!=s; v = prevEdge[v].other (v))
        {
            prevEdge[v].addFlowTo (v, delFlow);
//            System.out.println (v +" "+ prevEdge[v]);
        }
    }

    int n;
    int s;
    int t;
    boolean visited[];
    DiEdge prevEdge[]; //stores edge used to get to v in bfs
    private boolean hasAugPathBFS()
    {
        visited = new boolean[n];
        prevEdge = new DiEdge[n];
        //start bfs from s
        Queue<Integer> q = new LinkedList<Integer> ();
        q.add (s);
        visited[s] = true;
        prevEdge[s] = null;
        while (!q.isEmpty ())
        {
            int u = q.remove ();
            for (DiEdge e : G[u])
            {
                int v = e.other (u);
                if (visited[v] || e.getResidualCapacityTo (v) == 0)
                {
                    continue;
                }
                visited[v] = true;
                prevEdge[v] = e;
                q.add (v);
            }
        }
        return visited[t];
    }

    public boolean[] getMinCut()
    {
        return visited;
    }

}