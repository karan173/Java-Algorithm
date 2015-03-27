package Interviews;


import ArrayUtils.ArrayUtils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 28/7/13
 * Time: 3:02 AM
 * To change this template use File | Settings | File Templates.
 */
//tested at UVA
public class maxSum2D
{
    //O(n3)
    static int matrix[][];
    static int n;
    static int m;
    static int sumCols[][];
    //sumCols[i][j] = sum of column i upto the jth row
    //i.e sum[r][c] where r->[0,r] and c->[i,i]
    public static int getMaxSum2D(int a[][])
    {
        matrix = a;
        n = a.length;
        m = a[0].length;
        preprocessColumnSums ();
        int maxSum = Integer.MIN_VALUE;
        for (int startRow = 0; startRow < n; startRow++)
        {
            for (int endRow = startRow; endRow < n; endRow++)
            {
                //get best columns between these two rows
                int columnSums[] = getColumnSum (startRow, endRow);
                long KadaneArray[] = ArrayUtils.getKadaneArray (columnSums);
                for (long ans : KadaneArray)
                {
                    maxSum = (int) Math.max (ans, maxSum);
                }
            }
        }
        return maxSum;
    }

    private static int[] getColumnSum(int startRow, int endRow)
    {
        int colSums[] = new int[m];
        for (int i = 0; i < m; i++)
        {
            colSums[i] = sumCols[i][endRow] - ((startRow > 0) ? sumCols[i][startRow - 1] : 0);
        }
        return colSums;
    }

    private static void preprocessColumnSums()
    {
        sumCols = new int[m][n];
        for (int i = 0; i < m; i++)
        {
            sumCols[i][0] = matrix[0][i];
            for (int j = 1; j < n; j++)
            {
                sumCols[i][j] = sumCols[i][j - 1] + matrix[j][i];
            }
        }
    }
}
