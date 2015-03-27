package Maths;

import java.util.Arrays;

/**
 * Created by ksb on 18-11-2014.
 */
public class GaussianElimination
{
    public double det;
    public int rank;
    public double ans[];
    /*
    complexity = O(N^3)
    a is coefficient matrix along with the answer vector
    AX = B , so a is A + B column

    if a row is divided by some constant, det is multiplied by it
    if rows are swapped, deteminant is multiplied by -1


     */
    static final double EPS = 1e-7;

    //returns no of solutions
    //det computation not checked
    public int gauss (double a[][])
    {
        int n = a.length;
        int m = a[0].length - 1;
        int where[] = new int[m];
        Arrays.fill (where, -1);
        ans = new double[m];
        rank = 0;
        for (int col=0, row=0; col<m && row<n; ++col)
        {
            int sel = row;
            for (int i=row; i<n; ++i)
                if (Math.abs (a[i][col]) > Math.abs (a[sel][col]))
                    sel = i;
            if (Math.abs (a[sel][col]) < EPS)
            {
                det = 0;
                continue;
            }
            rank++;
            if(sel != row)
            {
                double t[] = a[sel];
                a[sel] = a[row];
                a[row] = t;
                det *= -1;
            }

            where[col] = row;
            det *= a[row][col];
            for (int i = 0; i < n; ++i)
            {
                if (i != row)
                {
                    double c = a[i][col] / a[row][col];
                    for (int j=col; j<=m; ++j)
                    {
                        a[i][j] -= a[row][j] * c;
                    }
                }
            }
            ++row;
        }
        for (int i=0; i<m; ++i)
            if (where[i] != -1)
                ans[i] = a[where[i]][m] / a[where[i]][i];
        for (int i=0; i<n; ++i) {
            double sum = 0;
            for (int j=0; j<m; ++j)
                sum += ans[j] * a[i][j];
            if (Math.abs (sum - a[i][m]) > EPS)
                return 0;
        }

        for (int i=0; i<m; ++i)
            if (where[i] == -1)
                return Integer.MAX_VALUE;
        return 1;
    }
}
