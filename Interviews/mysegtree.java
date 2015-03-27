package Interviews;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by ksb on 10-09-2014.
 */
public class mysegtree
{
    static class SegTree
    {
        int min[];
        int n;
        int a[];
        SegTree(int n, int a[])
        {
            this.n = n;
            min = new int[4 * n + 1];
            this.a = a;
            init(1,1,n);
        }

        private void init(int idx, int s, int e)
        {
            if(s == e)
            {
                min[idx] = a[s - 1];

                return;
            }
            int mid = (s+e)/2;
            init (2 * idx, s, mid);
            init (2 * idx + 1, mid + 1, e);
            min[idx] = Math.min (min[2 * idx], min[2 * idx + 1]);
        }

        int getAns(int l, int r)
        {
            return query (1, l, r, 1, n);
        }
        private int query(int idx, int l, int r, int s, int e)
        {
            if (s >= l && e <= r)
            {
                return min[idx];
            }
            if (s > r || e < l)
            {
                return Integer.MAX_VALUE >> 1;
            }
            int mid = (s + e) >> 1;
            return Math.min (query (2 * idx, l, r, s, mid), query (2 * idx + 1, l, r, mid + 1, e));
        }

    }

    public static void main(String[] args)
    {
        int a[] = {1,3,4,2,4,6,3,2,3,4};
        int n = a.length;
        SegTree segTree = new SegTree (n, a);
        System.out.println (Arrays.toString (segTree.min));
        Scanner scanner = new Scanner (new InputStreamReader (System.in));
        while (scanner.hasNext ())
        {
            int l = scanner.nextInt ();
            int r = scanner.nextInt ();
            System.out.println (segTree.getAns (l, r));
        }
    }


}
