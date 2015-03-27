package Interviews;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 29/6/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class maxProductSubarray
{
    public static int maxProductSubarray(int a[])
    {
        int maxSoFar = Integer.MIN_VALUE;
        int maxNegativeEndingHere = 0; //0 is invalid value  i.e most negative
        int maxPositiveEndingHere = 0;
        for (int i = 0; i < a.length; i++)
        {
            if (a[i] == 0)
            {
                maxPositiveEndingHere = maxNegativeEndingHere = 0;
                maxSoFar = Math.max (0, maxSoFar);    //for {20, 0} case
            }
            else if (a[i] > 0)
            {
                if (maxPositiveEndingHere != 0)
                {
                    maxPositiveEndingHere *= a[i];
                }
                else
                {
                    maxPositiveEndingHere = a[i];
                }

                if (maxNegativeEndingHere != 0)
                {
                    maxNegativeEndingHere *= a[i];
                }
                else
                {
                    maxNegativeEndingHere = 0;
                }
            }
            else  //<0
            {
                int p = maxPositiveEndingHere;
                int n = maxNegativeEndingHere;
                if (p != 0)
                {
                    maxNegativeEndingHere = p * a[i];
                }
                else
                {
                    maxNegativeEndingHere = a[i];
                }

                if (n != 0)
                {
                    maxPositiveEndingHere = n * a[i];
                }
                else
                {
                    maxPositiveEndingHere = 0;
                }
            }

            if (maxPositiveEndingHere != 0)
            {
                maxSoFar = Math.max (maxSoFar, maxPositiveEndingHere);
            }
            if (maxNegativeEndingHere != 0)      //for  {-20} case
            {
                maxSoFar = Math.max (maxSoFar, maxNegativeEndingHere);
            }
//            System.out.println (maxNegativeEndingHere + " " + maxPositiveEndingHere + " " + maxSoFar);
        }
        return maxSoFar;
    }

    public static void main(String[] args)
    {
        int arr[][] = {
                {1, -2, -3, 0, 7, -8, -2},
                {2, -3, -2, -40},
                {-20, 0, 0},
                {-20, 3},
                {-20},
                {-1, -15, -30, 0, -17, 2},
                {0, 0, 0},
                {6, -3, -10, 0, 2},
                {-1, -3, -10, 0, 60},

        };

        for (int i = 0; i < arr.length; i++)
        {
            System.out.println (maxProductSubarray (arr[i]));
        }
    }
}
