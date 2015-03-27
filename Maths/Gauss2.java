package Maths;

import java.util.Arrays;

/**
 * Created by ksb on 18-11-2014.
 */
public class Gauss2
{
    public int rank;
    public int ans[];

    /*
    complexity = O(N^3)
    a is coefficient matrix along with the answer vector
    AX = B , so a is A + B column

    if a row is divided by some constant, det is multiplied by it
    if rows are swapped, deteminant is multiplied by -1


     */
    //returns no of solutions
    //det computation not checked
    public int gauss (int a[][])
    {
        int n = a.length;
        int m = a[0].length - 1;
        int where[] = new int[m];
        Arrays.fill (where, -1);
        ans = new int[m];
        rank = 0;
        for (int col=0, row=0; col<m && row<n; ++col)
        {
            int sel = row;
            for (int i=row; i<n; ++i)
                if (a[i][col] == 1)
                {
                    sel = i;
                    break;
                }
            if (a[sel][col] == 0)
            {

                continue;
            }
            rank++;

            if(sel != row)
            {
                int t[] = a[sel];
                a[sel] = a[row];
                a[row] = t;
            }

            where[col] = row;
            for (int i = 0; i < n; ++i)
            {
                if (i != row && a[i][col] == 1)
                {
                    for (int j=col; j<=m; ++j)
                    {
                        a[i][j] -= a[row][j];
                        a[i][j] = Math.abs (a[i][j]);
                    }
                }
            }
            ++row;
        }

        for (int i=0; i<m; ++i)
            if (where[i] != -1)
                ans[i] = a[where[i]][m] / a[where[i]][i];
        for (int i=0; i<n; ++i) {
            int sum = 0;
            for (int j=0; j<m; ++j)
                sum += ans[j] * a[i][j];
            if (sum - a[i][m] != 0)
                return 0;
        }

        for (int i=0; i<m; ++i)
            if (where[i] == -1)
                return Integer.MAX_VALUE;
        return 1;
    }




}
