package Graphs;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 18/10/13
 * Time: 4:53 AM
 * To change this template use File | Settings | File Templates.
 */
//public class JohnsonAPSP
//{
//    /*
//    for APSP in graphs with -ve costs
//    = 1 bellman ford(for reweighing to remove -ve costs) and n dijkstras
//    O(mnlogn) if dijkstra using heaps
//    else O(n^3)
//    in sparse graphs O(n^2 logn) -> better than floyd warshall
//    returns false if -ve cycle in graph
//    note modifies graph in argument with edges of dummy vertex
//     */
//    public long ans[][];
//    public long vertexWeights[];
//    /*
//    note ans[i][n] contains a reduntant value, shortest path from i to
//     */
//    public boolean getAPSP(ArrayList<BellmanFord.InEdge> G[])
//    {
//        int n = G.length;
//        /*
//        make shallow copy of graph with an added dummy vertex
//        add edges from dummy vertex to all vertices with 0 cost
//        not original graph is also modified
//         */
//        ArrayList<BellmanFord.InEdge> GCopy[] = new ArrayList[n + 1];
//        for (int i = 0; i < n; i++)
//        {
//            GCopy[i] = G[i]; //shallow copy
//            GCopy[i].add (new BellmanFord.InEdge (n, 0));
//        }
//        GCopy[n] = new ArrayList<BellmanFord.InEdge> ();
//        /*
//        copy done
//         */
//
//        BellmanFord BF = new BellmanFord ();
//        if (!BF.getShortestPaths (GCopy, n))   //-ve cycle
//        {
//            return false;
//        }
//        ans = new long[n][n];
//        vertexWeights = BF.minDist;
//        BF = null;
//
//        /*
//        transform edges and return dijkstra graph
//         */
//        ArrayList<Dijkstra.Pair>[] dijkstraGraph = reweigh (GCopy, vertexWeights);
//
//        /*
//        run n dijkstras
//         */
//
//        for (int i = 0; i < n; i++) //dont do for dummy vertex
//        {
//            Dijkstra dijkstra = new Dijkstra ();
//            long sol[] = dijkstra.dijkstra (i, dijkstraGraph);
//            for (int j = 0; j < n; j++)
//            {
//                if (sol[j] >= Integer.MAX_VALUE)
//                {
//                    ans[i][j] = Integer.MAX_VALUE;
//                    continue;
//                }
//                ans[i][j] = sol[j] - vertexWeights[i] + vertexWeights[j];
//            }
//        }
//        return true;
//    }
//
//    /*
//    note we let dummy vertex remain in graph since it doesnt disturb shortest paths
//     */
//    private ArrayList<Dijkstra.Pair>[] reweigh(ArrayList<BellmanFord.InEdge>[] gCopy, long[] vertexWeights)
//    {
//        int n = gCopy.length;
//        ArrayList<Dijkstra.Pair> G[] = new ArrayList[n];
//        for (int i = 0; i < n; i++)
//        {
//            G[i] = new ArrayList<Dijkstra.Pair> ();
//        }
//        for (int i = 0; i < n; i++)
//        {
//            for (BellmanFord.InEdge e : gCopy[i])
//            {
//                e.cost = e.cost + vertexWeights[e.from] - vertexWeights[i];
//                if (e.cost < 0)
//                {
//                    throw new RuntimeException ();
//                }
//                G[e.from].add (new Dijkstra.Pair (i, e.cost));
//            }
//        }
//        return G;
//    }
//
//}
