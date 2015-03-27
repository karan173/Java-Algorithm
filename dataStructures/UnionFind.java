package dataStructures;

import java.util.Arrays;

//using weighted quick union algo with path compression
// O(N + MLg* N) N= vertices M = edges , java algo course
//0 indexed
public class UnionFind
{
    public int id[]; //id[i] = immediate parent of i
    public int size[]; //  size[i] = number of objects in subtree rooted at i incl i
    //NOTE - dont forget to manipulate size
    public int numOfComponents;
    public boolean isConnected(int p, int q)
    {
        return root(p) == root(q);
    }
    public UnionFind(int n)
    {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < id.length; i++)
        {
            id[i] = i;
            size[i] = 1;
        }
        numOfComponents = n;
    }
    
    public int root(int p)  //recursive elegant method
    {
        if(p!=id[p])
            id[p] = root(id[p]); //topmost parent, path compression
        return id[p];
    }
    
    public void union(int p, int q)
    {
        int root1 = root(p);
        int root2 = root(q);
        if(root1 == root2) //already connected
            return;
        if(size[root1]<size[root2])
        {
            id[root1] = root2;
            size[root2] += size[root1];
        }
        else
        {
            id[root2] = root1;
            size[root1] += size[root2];
        }
        numOfComponents--;
    }
    @Override
    public String toString()
    {
        
        StringBuilder s = new StringBuilder();
        s.append("id array\n");
        s.append(Arrays.toString(id));
        s.append("size arr\n");
        s.append(Arrays.toString(size));
        return s.toString();
    }
}
