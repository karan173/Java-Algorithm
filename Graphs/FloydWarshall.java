package Graphs;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 17/10/13
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class FloydWarshall
{
    /*
    NOTE WORKS IN DIRECTED/UNDIRECTED GRAPHS
    ALSO WORKS FR GRAPHS WITH -VE EDGE COSTS
    BUT JOHNSON BETTER FOR APSP IN SPARSE GRAPHS WITH -VE EDGES
    AND N DIJKSTRAS BETTER FOR APSP IN SPARSE GRAPHS WITH +VE EDGES

    note the dist matrix must have all edges satisfied
    dist[i][j] = 0 if i==j
               = min[cij] if there are several edges b/w i,j  [imp]
               = INF if no edge b/w i,j
    SET INF so that no overflow while addition, Integer.MaxVal will be ok
    Note dist is modified to reflect shortest paths
    returns false if negative cycle in graph, else true

    O(n^3)
     */
    public boolean getAPSP(int dist[][])
    {
        int n = dist.length;
        for (int k = 0; k < n; k++)
        {
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    dist[i][j] = Math.min (dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        for (int i = 0; i < n; i++)
        {
            if (dist[i][i] < 0)
            {
                return false;
            }
        }
        return true;
    }
}
