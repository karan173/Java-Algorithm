package Graphs;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 17/10/13
 * Time: 2:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class isBipartite
{
    int color[];
    int n;
    /*
    O(n+m)
     */
    public boolean isBipartite(List<Integer> G[])
    {
        n = G.length;
        color = new int[n];
        Arrays.fill (color, -1);
        for (int i = 0; i < n; i++)
        {
            if (color[i] == -1)     //not visited yet
            {
                color[i] = 0;
                if(!dfs (i, G))
                    return false;
            }
        }
        return true;
    }


    private boolean dfs(int u, List<Integer>[] G)
    {
        boolean retVal = true;
        int newColor = (color[u] + 1) & 1;
        for (int v : G[u])
        {
            if (color[v] == -1)       //not visited yet
            {
                color[v] = newColor;
                retVal &= dfs (v, G);
                if (retVal == false)
                {
                    return retVal;
                }
            }
            else if (color[v] != newColor)
            {
                return false;
            }
        }
        return true;
    }
    /*
    O(n^2)
     */
    public boolean isBipartite(boolean G[][])
    {
        n = G.length;
        color = new int[n];
        Arrays.fill (color, -1);
        for (int i = 0; i < n; i++)
        {
            if (color[i] == -1)     //not visited yet
            {
                color[i] = 0;
                if(!dfs (i, G))
                    return false;
            }
        }
        return true;
    }

    private boolean dfs(int u, boolean[][] G)
    {
        int newColor = (color[u] + 1) & 1;
        for (int v = 0; v < n; v++)
        {
            if (!G[u][v])
            {
                continue;
            }
            if (color[v] == -1)       //not visited yet
            {
                color[v] = newColor;
                if(!dfs (v, G))
                {
                    return false;
                }
            }
            else if (color[v] != newColor)
            {
                return false;
            }
        }
        return true;
    }
}
