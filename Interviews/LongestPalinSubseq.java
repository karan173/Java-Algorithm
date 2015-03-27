package Interviews;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 1/7/13
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongestPalinSubseq
{
    static int dp[][];
    public static int longestPalindromicSubseqLength(String s, int n)
    {
        char a[] = s.toCharArray ();
        dp = new int[n+1][0] ;
        //for length 1 and 2
        dp[0] = null;
        dp[1] = new int[n];
        dp[2] = new int[n];
        for (int i = 0; i < n-1; i++)
        {
            dp[1][i] = 1;
            dp[2][i] = (int) ((a[i] == a[i + 1]) ? (2) : (1));
        }
        dp[1][n-1] = 1;
        for (int len = 3; len <= n; len++)
        {
            dp[len] = new int[n];
            for (int i = 0; i < n + 1 - len; i++)
            {
                int j = i + len - 1;
                if (a[i] == a[j])
                {
                    dp[len][i] = (int) (2 + dp[len - 2][i + 1]);
                    continue;
                }
                dp[len][i] = (int) Math.max (dp[len - 1][i + 1], dp[len - 1][i]);
            }
            dp[len - 2] = null;
        }
        return dp[n][0];
    }
}
