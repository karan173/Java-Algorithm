package Flows;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 2/10/13
 * Time: 2:18 AM
 * To change this template use File | Settings | File Templates.
 */
/*
Complexity => If n1 and n2 vertices
Then O(NM) = O(n1 * n1n2) = O(n2*n1^2)
 */
 /*
    This is not exactly similar to the Ford Fulkerson Algorithm

    Note in each iteration we try to match the rth row with a column
    dfs(r)
        if r is not matched, it returns true if it can be matched to a column
        if r is matched, it returns true if we can match it to another column(other than the one it is currently matched

    Note in each iteration of for loop of maximalMatching, we only try to match the ith row[which is unmatched currently]
    All other rows remain unmatched if they were unmatched.
    If they were matched, they'll stil remain matched but may become matched to another column

    A row once matched always remains matched.
    A column once matched always remains matched.
    We can use these facts greedily to get a smallest lexicographic set or smallest set excluding some columns
    see problem Graduation topcoder

    If we are given some pairs, which must be matched - then we can just remove edges of those vertices and do the matching algo
    [see http://community.topcoder.com/stat?c=problem_statement&pm=10187&rd=13742]
    */

public class BipartiteMatching
{
    public int colMatch[];       //row matched with cth column
    boolean visitedRows[];
    boolean visitedCols[];
    int rows, cols;
    /*
    O(NM algorithm N = Min(rows, cols), M = no of edges
    since O(M) used for each dfs
     */


    public int maximalMatching(boolean adj[][])
    {
        rows = adj.length;
        cols = adj[0].length;
        colMatch = new int[cols];
        Arrays.fill (colMatch, -1);
        int flow = 0;
        for (int i = 0; i < rows; i++)
        {
            //row i will be unmatched currently
            visitedRows = new boolean[rows];
            visitedCols = new boolean[cols];
            if (dfs (i, adj))
            {
                flow++;
            }
        }
        return flow;
    }

    /*
    if row r is matched, try matching it to another column
    if unmatched, match
     */
    private boolean dfs(int r, boolean adj[][])
    {
        if (r == -1)   //we reached terminal node t
        {
            return true;
        }
        if (visitedRows[r])
        {
            return false;
        }
        visitedRows[r] = true;
        for (int c = 0; c < cols; c++)
        {
            if (visitedCols[c] || !adj[r][c])
            {
                continue;
            }
            visitedCols[c] = true;
            if (dfs (colMatch[c], adj))
            {
                colMatch[c] = r;
                return true;
            }
        }
        return false;
    }


    /*
    Adjacency List case
     */
    public int maximalMatchingAdjList(ArrayList<Integer> adj[], int cols)
    {
        rows = adj.length;
        this.cols = cols;
        colMatch = new int[cols];
        Arrays.fill (colMatch, -1);
        int flow = 0;
        for (int i = 0; i < rows; i++)
        {
            //row i will be unmatched currently
            visitedRows = new boolean[rows];
            visitedCols = new boolean[cols];
            if (dfs (i, adj))
            {
                flow++;
            }
        }
        return flow;
    }

    private boolean dfs(int r, ArrayList<Integer>[] adj)
    {
        if (r == -1)   //we reached terminal node t
        {
            return true;
        }
        if (visitedRows[r])
        {
            return false;
        }
        visitedRows[r] = true;
        for (int c : adj[r])
        {
            if (visitedCols[c])
            {
                continue;
            }
            visitedCols[c] = true;
            if (dfs (colMatch[c], adj))
            {
                colMatch[c] = r;
                return true;
            }
        }
        return false;
    }
}
