package Flows;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 24/11/13
 * Time: 1:16 AM
 * To change this template use File | Settings | File Templates.
 */
/*
checked
 */
public class BPM_Dinic
{
    /*
    O(m*sqrt(n))
     */
    /*
    i is 1 indexed
     */
    int getIndexA(int i)
    {
        return i;
    }
    /*
   i is 1 indexed
    */
    int getIndexB(int i)
    {
        return i+n1;
    }
    int n1, n2;
    /*
    a[i] b/w [1,n1]
    b[i] b/w [1,n2]
     */
    public int maxMatching(int a[], int b[], int n1, int n2)
    {
        this.n1 = n1;
        this.n2 = n2;
        int m = a.length;
        Dinic dinic;
        dinic = new Dinic (n1 + n2 + 2);
        int s = 0, e = n1 + n2 + 1;
        for (int i = 0; i < m; i++)
        {
            dinic.addEdge (getIndexA (a[i]), getIndexB (b[i]), 1);
        }
        for (int i = 1; i <= n1; i++)
        {
            dinic.addEdge (s, getIndexA (i),1);
        }
        for (int i = 1; i <= n2; i++)
        {
            dinic.addEdge (getIndexB (i), e, 1);
        }
        return (int) dinic.maxFlow (s, e, false);
    }

    public int maxMatching(boolean adj[][])
    {
        this.n1 = adj.length;
        this.n2 = adj[0].length;
        Dinic dinic = new Dinic (n1 + n2 + 2);
        int s = 0, e = n1 + n2 + 1;
        for (int i = 1; i <= n1; i++)
        {
            for (int j = 1; j <= n2; j++)
            {
                if (adj[i - 1][j - 1])
                {
                    dinic.addEdge (getIndexA (i), getIndexB (j), 1);
                }
            }
        }
        for (int i = 1; i <= n1; i++)
        {
            dinic.addEdge (s, getIndexA (i),1);
        }
        for (int i = 1; i <= n2; i++)
        {
            dinic.addEdge (getIndexB (i), e, 1);
        }
        return (int) dinic.maxFlow (s, e, false);
    }
}
