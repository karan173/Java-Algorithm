package Interviews;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 26/7/13
 * Time: 3:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class nqueens
{
    public static void main(String[] args)
    {
        nqueens x = new nqueens ();
        x.printAllWays (8);
    }
    int[] columns;
    int n;
    private void printAllWays(int n)
    {
        this.n = n;
        columns = new int[n];
        for (int i = 0; i < n; i++)
        {
            columns[i] = -1;
        }
        printWays (0);
    }
    int ways = 0;
    private void printWays(int queenNo)
    {
        int i = queenNo;
        //queenno also serves as stacktop and rowno
        if (queenNo == n)
        {
            ways++;
            System.out.println ("way - "+ways);
            printCurrentWay ();

            System.out.println ();
            return;
        }
        for (int j = 0; j < n; j++)
        {
            if (valid (i, j))
            {
                //use this cell
                columns[j] = i;
                printWays (queenNo + 1);
                columns[j] = -1;
//                isDiagnolInvalid[i][j] = true;
            }
        }
    }


    private boolean valid(int i, int j)
    {
        if (columns[j] != -1)
        {
            return false;
        }
        for (int k = 0; k < n; k++)
        {
            if (columns[k] != -1)
            {
                int j2 = k;
                int i2 = columns[k];
                int di = Math.abs (i2 - i);
                int dj = Math.abs (j2 - j);
                if (di == dj)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void printCurrentWay()
    {
        for (int i = 0; i < n; i++)
        {
            int col = i;
            int row = columns[i];
            System.out.println (row + " " + col);
        }
    }
}
