package ArrayUtils;
import InputOutput.FastReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ArrayUtils
{
    //up down left right
    public static int dx4[] = {0, 0, 1, -1};
    public static int dy4[] = {1, -1, 0, 0};

    //up down and diagnols
    public static int dx8[] = {0, 0, 1, 1, 1, -1, -1, -1};
    public static int dy8[] = {1, -1, 0, 1, -1, 0, 1, -1};

    public static int hexI[] = {0, 0, -1, -1, 1, 1};
    public static int hexJ[] = {-1, 1, 0, 1, -1, 0};
    public static int predIndex(int[] a, int x)
    {
        /**
         * returns index of element <= k, predecessor search O(lgn)
         */
        int low = 0;
        int n = a.length;
        int high = n - 1;
        while (low <= high)
        {
            int mid = (low + high) / 2;
            // System.out.println(low + " " + high);
            if (a[mid] > x)
                high = mid - 1;

            else
            {
                if (mid == n - 1 || a[mid + 1] > x)
                    return mid;
                else
                    low = mid + 1;
            }
        }
        return -1;
    }

    public static int sucIndex(int a[], int x)
    {
        /**
         * returns index of element >= k, successor search O(lgn)
         */
        int n = a.length;
        int low = 0, high = n - 1, mid;
        while (low <= high)
        {
            mid = (high + low) / 2;

            if (a[mid] < x)
            {
                low = mid + 1;
            }
            else
            {
                if (mid == 0 || a[mid - 1] < x)
                    return mid;
                else
                    high = mid - 1;
            }
        }
        return -1;
    }


    // simply prints all permutation - to see how it works
    public static<E extends Comparable<? super E>> void printPermutations(E[] c)
    {
        System.out.println(Arrays.toString(c));
        while ((c = nextPermutation(c)) != null)
        {
            System.out.println(Arrays.toString(c));
        }
    }

    // modifies c to next permutation or returns null if such permutation does
    // not exist
    public static <E extends Comparable<? super E>> E[] nextPermutation(final E[] c)
    {
        // 1. finds the largest k, that c[k] < c[k+1]
        int first = getFirst(c);
        if (first == -1)
            return null; // no greater permutation
        // 2. find last index toSwap, that c[k] < c[toSwap]
        int toSwap = c.length - 1;
        while (c[first].compareTo(c[toSwap]) >= 0)
            --toSwap;
        // 3. swap elements with indexes first and last
        swap(c, first++, toSwap);
        // 4. reverse sequence from k+1 to n (inclusive)
        toSwap = c.length - 1;
        while (first < toSwap)
            swap(c, first++, toSwap--);
        return c;
    }

    // finds the largest k, that c[k] < c[k+1]
    // if no such k exists (there is not greater permutation), return -1
    public static <E extends Comparable<? super E>> int getFirst(final E[] c)
    {
        for (int i = c.length - 2; i >= 0; --i)
            if (c[i].compareTo(c[i + 1]) < 0)
                return i;
        return -1;
    }

    // swaps two elements (with indexes i and j) in array
    public static void swap(Object c[], final int i, final int j)
    {
        Object tmp = c[i];
        c[i] = c[j];
        c[j] = tmp;
    }

    public static long[] getKadaneArray(int[] a)
    {
        int n = a.length;
        long maxSoFar[] = new long[n];
        long maxEndingHere = maxSoFar[0] = a[0];
        for (int i = 1; i < n; i++)
        {
            if(maxEndingHere < 0)
            {
                maxEndingHere = a[i];
            }
            else
            {
                maxEndingHere += a[i];
            }
            maxSoFar[i] = Math.max (maxSoFar[i - 1], maxEndingHere);
        }
        return maxSoFar;
    }

    public static void shuffle(int[] a)
    {
        Random rand = new Random ();
        int N = a.length;
        for (int i = 0; i < N; i++)
        {
            rand.setSeed (5874375);
            int r = rand.nextInt (i + 1);
            int t = a[i];
            a[i] = a[r];
            a[r] = t;
        }
    }
    public static void readIntArray(int a[], FastReader in)
    {
        for (int i = 0; i < a.length; i++)
        {
            a[i] = in.ni ();
        }
    }
    public static void read2DIntArray(int a[][], FastReader in)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] = in.ni ();
            }
        }
    }

    /*
    compress array a into b
    returns no of distinct values in array a which is the max value in b+1
    values in b vary from [0.....(distinct-1)]
     */
    public static int compress(int[] a, int[] b)
    {
        if (b == null)
        {
            b = new int[a.length];
        }
        for (int i = 0; i < a.length; i++)
        {
            b[i] = a[i];
        }
        Arrays.sort (b);   //sorted a
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
        int distinct = 0;
        for (int i = 0; i < b.length; i++)
        {
            int j;
            map.put (b[i], distinct++);
            for (j = i + 1; j < b.length && b[j] == b[i]; j++) ;
            i = j - 1;
        }
        //compress array a into b
        for (int i = 0; i < a.length; i++)
        {
            b[i] = map.get (a[i]);
        }
        return  distinct;
    }
    public long julianDay(long year, long month, long day) {
        long a = (14 - month) / 12;
        long y = year + 4800 - a;
        long m = month + 12 * a - 3;
        long jdn = day + (153 * m + 2)/5 + 365*y + y/4 - y/100 + y/400 - 32045;
        return jdn;
    }

    public long diff(long y1, long m1, long d1, long y2, long m2, long d2)
    {
        return julianDay (y2, m2, d2) - julianDay (y1, m1, d1);
    }

    //debugging utility
    public static void debug(Object...args)
    {
        System.out.println(Arrays.deepToString(args));
    }

}
