package dataStructures;

import Maths.MathUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 9/8/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
/*
a O(<O(nlogn, O(1)> solution to static Range Minimum Query problem
from http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=lowestCommonAncestor#Lowest Common Ancestor (LCA)
for dynamic use Segment tree <O(n), O(logn)>
This is Sparse Table Algo
 */
public class StaticRMQ
{
    int n;
    int dp[][];
    int a[];
    public StaticRMQ(int a[])
    {
        n = a.length;
        this.a = a;
        int k = MathUtils.ceilLog2 (n);
        //2^k<=n
        dp = new int[n][k+1];
        //dp[i][j] = index of min value in range [i, i+2^j)
        for (int i = 0; i < n; i++)
        {
            dp[i][0] = i;
        }
        //now dp[i][j] = index of Min of values at dp[i][j-1], dp[i+2^j][j-1]
        for (int j = 1; j < dp[0].length; j++)
        {
            for (int i = 0; i < n; i++)
            {
                int cand1 = a[dp[i][j - 1]];
                int cand2 = Integer.MAX_VALUE;
                int idx2 = i + (1 << (j - 1));
                if (idx2 < n)
                {
                    cand2 = a[dp[idx2][j - 1]];
                }
                if (cand1 <= cand2)
                {
                    dp[i][j] = dp[i][j - 1];
                }
                else
                {
                    dp[i][j] = dp[idx2][j - 1];
                }
            }
        }
    }

    /*
    get index of min value in range [a,b]
     */
    public int getMinIndex(int i, int j)
    {
        if (i > j || j >= n || i < 0)
        {
            throw new IllegalArgumentException ("[args= " + i + " " + j + " " + n + "]");
        }
        int n = j - i + 1;
        int k = MathUtils.ceilLog2 (n);
        int idx2 = j - (1 << k) + 1;

        //2^k <=n
        // System.out.println (String.format ("bound1 - [%d,%d] bound2 - [%d,%d]", i, i+(1<<k) - 1,j - (1 << k) + 1, j));
        int cand1 = a[dp[i][k]];          //from [i,i+2^k)
        int cand2 = a[dp[idx2][k]]; //from [j-2^k+1, j+1)
//        System.out.println (cand1 + " " + cand2);
        if (cand1 <= cand2)
        {
            return dp[i][k];
        }
        return dp[idx2][k];
    }
}