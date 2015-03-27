package Interviews;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 3/7/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongestCommonSubseq
{
    public static int lcsLen(String aa, String bb)
    {
        char a[] = aa.toCharArray ();
        char b[] = bb.toCharArray ();
        int n = a.length ;
        int m = b.length ;
        int dp[][] = new int[n+1][m+1];
        //dp[n][m] = dp[n-1][m-1] + 1 if a[n] == b[m]
        //else max dp[n-1][m], dp[n][m-1]
        for (int i = 0; i <= n; i++)
        {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= m; i++)
        {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                if (a[i-1] == b[j-1])
                {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else
                {
                    dp[i][j] = Math.max (dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
//        System.out.println (Arrays.deepToString (dp));
        return dp[n][m];
    }
}
