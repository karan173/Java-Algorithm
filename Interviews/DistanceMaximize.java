package Interviews;

/**
 * Created by ksb on 07-09-2014.
 */
//http://www.geeksforgeeks.org/given-an-array-arr-find-the-maximum-j-i-such-that-arrj-arri/
public class DistanceMaximize
{
    static int bestDistance(int a[])
    {
        int n = a.length;
        if (n == 0)
        {
            return -1;
        }
        int lmin[] = new int[n];
        lmin[0] = 0;
        for (int i = 1; i < n; i++)
        {
            lmin[i] = a[i] < a[lmin[i - 1]] ? i : lmin[i - 1];
        }
        int left = lmin[n - 1];
        int right = n - 1;
        int best = -1;
        while (left >= 0)
        {
            while (right > left && a[right] <= a[left])
            {
                right--;
            }
            if (right > left && a[right] > a[left])
            {
                best = Math.max (best, right - left);
            }
            if (left == 0)
            {
                break;
            }
            left = lmin[left - 1];
        }
        return best;
    }

    public static void main(String[] args)
    {
        int a[] = {1, 5, 4, 6, 10, 7, 8};
        System.out.println (bestDistance (a));
    }
}
