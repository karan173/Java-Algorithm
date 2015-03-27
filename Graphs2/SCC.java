package Graphs2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 4/10/13
 * Time: 3:08 AM
 * To change this template use File | Settings | File Templates.
 */
/*
tested
 */
public class SCC
{
    public int numComp; //number of SCC
    public int comp[];  //component number of vertex i from [0, numComp)
    int n;
    ArrayList<Integer> revG[];
    /*
    takes as input graph and its reverse
    Kosaraju two pass algorithm
    for explanation see techniques
    Also note that we have proceeded in topological order of actual Graph while calculation comp[]
     */
    public void getSCC(ArrayList<Integer> G[], ArrayList<Integer> revG[])
    {
        this.revG = revG;
        numComp = 0;
        n = G.length;
        comp = new int[n];
        Arrays.fill (comp, -1);
        int revPostOrder[] = new TopoSort ().topoSort (G); //actually not topo order, coz graph is cyclic
        for (int x : revPostOrder)
        {
            if (comp[x] == -1)
            {
                dfs (x);
                numComp++;
            }
        }
    }

    private void dfs(int i)
    {
        comp[i] = numComp;
        for (int v : revG[i])
        {
            if (comp[v] != -1)
            {
                continue;
            }
            dfs (v);
        }
    }
}
