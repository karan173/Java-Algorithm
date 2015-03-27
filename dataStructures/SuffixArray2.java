package dataStructures;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 8/8/13
 * Time: 4:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuffixArray2
{
    //Output:
    // pos = The suffix array. Contains the n suffixes of str sorted in lexicographical order.
    //       Each suffix is represented as a single integer (the position of str where it starts).
    // rank = The inverse of the suffix array. rank[i] = the index of the suffix str[i..n)
    //        in the pos array. (In other words, pos[i] = k <==> rank[k] = i)
    //        With this array, you can compare two suffixes in O(1): Suffix str[i..n) is smaller
    //        than str[j..n) if and only if rank[i] < rank[j]
    /*
    adapted from http://www.codechef.com/viewsolution/2322721
    which inturn is adapted from
    http://apps.topcoder.com/forums/?module=RevisionHistory&messageID=1171511
     */
    private final int n;
    private final char[] str;
    public Integer[] pos;
    public int[] rank;
    private int[] cnt;
    private int[] next;
    public int[] lcp;
    private boolean[] bh;
    private boolean[] b2h;

    public SuffixArray2(char[] str)
    {
        this.n = str.length;
        this.str = str;
        this.pos = new Integer[n];
        this.rank = new int[n];
        this.cnt = new int[n];
        this.next = new int[n];
        this.lcp = new int[n];
        this.bh = new boolean[n];
        this.b2h = new boolean[n];
//        suffixSort();
//        computeLCP();
    }

    public void suffixSort()
    {
        for (int i = 0; i < n; ++i)
            pos[i] = i;
        Arrays.sort (pos, 0, n, new Comparator<Integer> ()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return str[o1] - str[o2];
            }
        });
        for (int i = 0; i < n; ++i)
        {
            bh[i] = i == 0 || str[pos[i]] != str[pos[i - 1]];
            b2h[i] = false;
        }
        for (int h = 1; h < n; h <<= 1)
        {
//            if (h >= 2 * lim)  //CHECK
//            {
//                return;
//            }
            int buckets = 0;
            for (int i = 0, j; i < n; i = j)
            {
                j = i + 1;
                while (j < n && !bh[j]) ++j;
                next[i] = j;
                ++buckets;
            }
            if (buckets == n)
            {
                break;
            }
            for (int i = 0; i < n; i = next[i])
            {
                cnt[i] = 0;
                for (int j = i; j < next[i]; ++j)
                    rank[pos[j]] = i;
            }
            ++cnt[rank[n - h]];
            b2h[rank[n - h]] = true;
            for (int i = 0; i < n; i = next[i])
            {
                for (int j = i; j < next[i]; ++j)
                {
                    int s = pos[j] - h;
                    if (s >= 0)
                    {
                        int head = rank[s];
                        rank[s] = head + cnt[head]++;
                        b2h[rank[s]] = true;
                    }
                }
                for (int j = i; j < next[i]; ++j)
                {
                    int s = pos[j] - h;
                    if (s >= 0 && b2h[rank[s]])
                    {
                        for (int k = rank[s] + 1; k < n && !bh[k] && b2h[k]; ++k)
                            b2h[k] = false;
                    }
                }
            }
            for (int i = 0; i < n; ++i)
            {
                pos[rank[i]] = i;
                bh[i] |= b2h[i];
            }
        }
        for (int i = 0; i < n; ++i)
            rank[pos[i]] = i;
    }

    public void computeLCP()
    {
        for (int i = 0, h = 0; i < n; ++i)
        {
            if (rank[i] > 0)
            {
                int j = pos[rank[i] - 1];
                while (i + h < n && j + h < n && str[i + h] == str[j + h]) ++h;
                lcp[rank[i]] = h;
                if (h > 0)
                {
                    --h;
                }
            }
        }
    }

    public int countSubstring(char s[])
    {
        int k = s.length;
        int hi = getMaxMatchingIndex (s, 0, n - 1, k);
        if (hi == -1)
        {
            return 0;
        }
        int lo = getLeastMatchingIndex (s, 0, n - 1, k);
        return hi - lo + 1;
    }


    public int getMaxMatchingIndex(char[] s, int lo, int hi, int len)
    {
        if (lo > hi)
        {
            return -1;
        }
        int mid = (lo + hi) >> 1;
        int cmp = compare (s, 0, len - 1, pos[mid], Math.min (pos[mid] + len - 1, n - 1), str);
        if (cmp < 0)     //s is less, mid is greater
        {
            return getMaxMatchingIndex (s, lo, mid - 1, len);
        }
        else if (cmp > 0)
        {
            return getMaxMatchingIndex (s, mid + 1, hi, len);
        }
        else  //matches
        {
            return Math.max (getMaxMatchingIndex (s, mid + 1, hi, len), mid);
        }
    }

    private int compare(char[] s1, int lo1, int hi1, int lo2, int hi2, char[] s2)
    {
        int i1, i2;
        for (i1 = lo1, i2 = lo2; i1 <= hi1 && i2 <= hi2; i1++, i2++)
        {
            int z = s1[i1] - s2[i2];
            if (z != 0)
            {
                return z;
            }
        }
        if (i1 <= hi1)   //i2 bigger
        {
            return 1;
        }
        if (i2 <= hi2)
        {
            return -1;
        }
        return 0;
    }

    public int getLeastMatchingIndex(char[] s, int lo, int hi, int len)
    {
        if (lo > hi)
        {
            return Integer.MAX_VALUE;
        }
        int mid = (lo + hi) >> 1;
        int cmp = compare (s, 0, len - 1, pos[mid], Math.min (pos[mid] + len - 1, n - 1), str);
        if (cmp < 0)     //s is less, mid is greater
        {
            return getLeastMatchingIndex (s, lo, mid - 1, len);
        }
        else if (cmp > 0)
        {
            return getLeastMatchingIndex (s, mid + 1, hi, len);
        }
        else  //matches
        {
            return Math.min (getLeastMatchingIndex (s, lo, mid - 1, len), mid);
        }
    }
}