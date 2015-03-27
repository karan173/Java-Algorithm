package Flows;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 27/11/13
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */

/*
doesn't work with PQ correctly. Reason you can't modify keys of elements in PQ currently.
Hence I had been implementing PQ dijkstra incorrectly.
Hence we need to store priority inside the element itself
 */
public class MinCostMaxFlow
{
    /*
    complexity = O(max_flow * mlogn)
    note this algo also returns min cost for a given particular value of maxflow
    Note we can also replace mlogn by n^2 dijkstra for dense graphs
    CAN ONLY BE USED WHEN GRAPH CONTAINS NO -VE COST CYCLE since we cant even use Bellman ford to find shortest paths
    IF -VE COST CYCLE USE CYCLE CANCELLING ALGO
    */

    /*
     takes as input the "TRANSFORMED NETWORK" with dummy source sink added to account for vertex supply/demands
     if netflow != maxflow, it means a feasible solution doesnt exist
     */

    public ArrayList<Edge> G[];
    int n;
    static final long INF = Integer.MAX_VALUE;
    public long netFlow, netCost;
    public MinCostMaxFlow(int n)
    {
        this.n = n;
        G = new ArrayList[n];
        for (int i = 0; i < n; i++)
        {
            G[i] = new ArrayList<Edge> ();
        }
    }
    public Edge addEdge(int from, int to, long cap, long cost)
    {
        Edge e = new Edge (from, to, cap, cost);
        G[from].add (e);
        G[to].add (e);
        return e;
    }
    public long[] pot;

    /*
    maxF is the value of flow for which min cost is to be found
    verify after termination whether this flow is achieved or not
    in transformed network = maxF = sum of demands
    returns false if no feasible solution netFlow < maxF
     */
    public boolean solve(int s, int t, long maxF, boolean hasNegativeEdges)
    {
        netCost = netFlow = 0;
        if (hasNegativeEdges)
        {
            bellmanFord(s);
        }
        else
        {
            pot = new long[n];
        }
        /*
        if cost are positive, initial potential is set to 0, cijpi = cij - pi + pj
        hence p = 0, cijpi = cij >=0 hence satisfies reduced cost optimality conditions initially

        if cost are -ve, we find shortest distances using bellman ford and set pi = -di since
        cijpi = cij - pi + pj = cij + di - dj
        but for shortest paths dj<=cij + di hence  cijpi >=0 initially
         */

        /* for dijkstra */
        final long minDist[] = new long[n];
        Edge prevEdge[] = new Edge[n];
//        boolean done[] = new boolean[n];
        long bottleNeckFlow[] = new long[n]; //bottleneck flow on shortest path from s to i
        TreeSet<Integer> pq = new TreeSet<Integer> (new Comparator<Integer> ()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                int cmp = Long.valueOf (minDist[o1]).compareTo (minDist[o2]);
                if (cmp == 0)
                {
                    return Integer.valueOf (o1).compareTo (o2);
                }
                return cmp;
            }
        });
        int ctr = 0;
        while (netFlow < maxF)
        {
            ctr++;
            //dijkstra
            pq.clear ();
            Arrays.fill (minDist, INF);
//            Arrays.fill (done, false);
            minDist[s] = 0;
            bottleNeckFlow[s] = INF;
            pq.add (s);
            while (!pq.isEmpty ())
            {
                int u = pq.pollFirst ();
                for (Edge e : G[u])
                {
                    int v = e.other (u);
                    long resCap = e.getResidualCapacityTo (v);
                    if (resCap == 0 )      //not in residual graph
                    {
                        continue;
                    }
                    //Reduced cost = cost + pu - pv
                    long newDist = minDist[u] + e.getCostTo (v) + pot[u] - pot[v];
                    if (e.getCostTo (v) + pot[u] - pot[v] < 0)
                    {
                        throw new RuntimeException ();
                    }
                    if (newDist < minDist[v])
                    {
                        pq.remove (v);
                        minDist[v] = newDist;
                        prevEdge[v] = e;
                        pq.add (v);
                        bottleNeckFlow[v] = Math.min (bottleNeckFlow[u], resCap);
                    }
                }
            }
            if (minDist[t]==INF)   //maximum flow found, no more augmenting paths in residual graph
            {
                return false;
            }
            //dijkstra done

            //update potentials
            for (int i = 0; i < n; i++)
            {
                pot[i] += minDist[i];
            }
            long delFlow = Math.min (bottleNeckFlow[t], maxF - netFlow);
            netFlow += delFlow;

            //augment flow
            int v = t;
            while (v != s)
            {
                //actual followed edge is u->v , we are going in backward direction
                Edge e = prevEdge[v];
                e.addFlowTo (v, delFlow);
                netCost += delFlow * e.getCostTo (v);
                v = e.other (v);
            }
        }
        return true;
    }

    /*
    sets initial potentials by finding shortest path distances in graph with -ve edge costs
     */
    private void bellmanFord(int s)
    {
        long dp[][] = new long[n][];
        dp[0] = new long[n];
        Arrays.fill (dp[0], INF);
        dp[0][s] = 0;
        boolean changed = false;
        for (int i = 1; i < n; i++)
        {
            changed = false;
            dp[i] = new long[n];
            for (int j = 0; j < n; j++)
            {
                dp[i][j] = dp[i - 1][j];
                for (Edge e : G[j])
                {
                    if (e.v != j)  //not incoming edge
                    {
                        continue;
                    }
                    int k = e.u;
                    //use edge k->j
                    if (e.getResidualCapacityTo (j) > 0 && (dp[i - 1][k] + e.getCostTo (j) < dp[i][j]))
                    {
                        dp[i][j] = dp[i - 1][k] + e.getCostTo (j);
                        changed = true;
                    }
                }
            }
            dp[i - 1] = null;
            if (!changed) //done - no more changes
            {
                pot = dp[i];
                break;
            }
        }
        if (changed)
        {
            pot = dp[n - 1];
        }
    }
    public class Edge
    {
        public int u, v;
        public long capacity;
        public long flow;
        private long cost;
        public Edge(int u, int v, long capacity, long cost)
        {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
            this.cost = cost;
            flow = 0;
            assert capacity >= 0;
        }

        public int other(int x)
        {
            if (x == u)
            {
                return v;
            }
            if (x == v)
            {
                return u;
            }
            throw new IllegalArgumentException ();
        }

        public long getResidualCapacityTo(int x)
        {
            if (x == u)
            {
                return flow;
            }
            if (x == v)
            {
                return capacity - flow;
            }
            throw new IllegalArgumentException ();
        }

        public void addFlowTo(int x, long delFlow)
        {
            if (x == u)
            {
                flow -= delFlow;
                assert flow >= 0;
            }
            else if (x == v)
            {
                flow += delFlow;
                assert flow <= capacity;
            }
            else
            {
                throw new IllegalArgumentException ();
            }
        }



        public long getCostTo(int x)
        {
            return (x == u) ? -cost : cost;
        }

        public String toString()
        {
            return "Edge{" +
                    "u=" + u +
                    ", v=" + v +
                    ", capacity=" + capacity +
                    ", flow=" + flow +
                    ", cost=" + cost +
                    '}';
        }
    }
}

