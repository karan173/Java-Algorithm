package Flows;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 19/10/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
/*
tested on tc srm 371 div1 lvl2
 */
public class MinCostMaxFlowBF
{
    /*
    also doesn't support multiple edges between same nodes = 1 forward and 1 backward is ok provided they have same cost
    also multiple ok, if they have same costs, just add their capacities
    hence must satisfy cost[i][j] = -cost[j][i]

    complexity = O(mn * max_flow)
    takes as input the "TRANSFORMED NETWORK" with dummy source sink added to account for vertex supply/demands
    if netflow != maxflow, it means a feasible solution doesnt exist
     */

    /*
    we dont modify origianl capacities since we may need to find flow value b/w 2 vertices
     */
    public int residualCapacity[][];

    /*
    cost[i][j] = -cost[j][i]
     */
    int cost[][];

    public int netFlow;
    public int  netCost;
    /*
    original capacities provided, multiple edges allowed only if they have same cost
     */
    int origianlCap[][];
    int s, t;
    int n;
    static final int INF = Integer.MAX_VALUE/2;
    public void solve(int cap[][], int cost[][], int s, int t)
    {
        origianlCap = cap;
        this.cost = cost;
        n = cost.length;
        this.s = s;
        this.t = t;
        residualCapacity = new int[n][n];
        copyResidualCapacities();
        netFlow = 0;
        netCost = 0;

        while (true)
        {
            /*
            find shortest(cost wise) augmenting path from s to t
             */
            bellmanFord();
            if (pred[t]<0) //no more augmenting path
            {
                return;
            }
            /*
            augment flow along found shortest path
             */
            augmentFlow();
        }


    }

    int pred[];

    private void bellmanFord()
    {
        pred = new int[n];
        int dp[][] = new int[n][];
        dp[0] = new int[n];
        Arrays.fill (dp[0], INF);
        Arrays.fill (pred, -1);
        dp[0][s] = 0;
        boolean changed = false;
        for (int i = 1; i < n; i++)
        {
            changed = false;
            dp[i] = new int[n];
            for (int j = 0; j < n; j++)
            {
                dp[i][j] = dp[i - 1][j];
                for (int k = 0; k < n; k++)
                {
                    //use edge k->j
                    if (residualCapacity[k][j] > 0 && (dp[i - 1][k] + cost[k][j] < dp[i][j]))
                    {
                        dp[i][j] = dp[i - 1][k] + cost[k][j];
                        pred[j] = k;
                        changed = true;
                    }
                }
            }
            dp[i - 1] = null;
            if (!changed) //done - no more changes
            {
                break;
            }
        }
        //-ve cycle, coz changed = true implies we exited normally, and in last iteration, some value changed hence -ve cycle
    }

    private void augmentFlow()
    {
        int bottleNeckCapacity = INF;
        int u = t;
        while (u != s)
        {
            /*
            note actually we follow edge pred[u] -> u
             */
            bottleNeckCapacity = Math.min (bottleNeckCapacity, residualCapacity[pred[u]][u]);
            u = pred[u];
        }
        u = t;
        while (u != s)
        {
            augmentFlow (pred[u], u, bottleNeckCapacity);
            u = pred[u];
        }
        netFlow += bottleNeckCapacity;
    }

    private void augmentFlow(int u, int v, int delFlow)
    {
        netCost += delFlow * cost[u][v];
        residualCapacity[u][v] -= delFlow;
        residualCapacity[v][u] += delFlow;
    }


    private void copyResidualCapacities()
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                residualCapacity[i][j] = origianlCap[i][j];
            }
        }
    }


}
