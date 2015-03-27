package dataStructures;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 7/8/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuffixArray
{
    //    String ss;
    char str[];
    //only limit is changed, for searching substrings of max length lim
    public void getSuffixArray(int lim)
    {
        //sort suffices by first character
        Arrays.sort (suffix);
        updateRanks ();
//        for (int i = 0; i < n; i++)
//        {
//            System.out.println (suffix[i].idx+" "+ ss.substring (suffix[i].idx) + " "+ rank[suffix[i].idx]);
//        }
//        System.out.println ("----------");
        for (gap = 1, lvl = 1; gap * 2 <= Math.min (n, lim); gap += gap, lvl++)    //check exit condition
        {
            Arrays.sort (suffix);
            //now has 2*gap same or 1<<lvl same

//            System.out.println ("----------");
            //now the suffices are sorted based on first gap characters
            updateRanks ();
//            for (int i = 0; i < n; i++)
//            {
//                System.out.println (suffix[i].idx+" "+ ss.substring (suffix[i].idx) + " "+ rank[suffix[i].idx]);
//            }
//            System.out.println ("----------");

            if (rank[lvl][suffix[n - 1].idx] == n - 1)
            {
                break;
            }
        }
//        int SA[] = new int[n];
//        for (int i = 0; i < n; i++)
//        {
//            SA[i] = suffix[i].idx;
//        }
//        return SA;
    }

    public int countSubstring(String s)
    {
//        int k = s.length ();
//        int hi = getMaxMatchingIndex (s, 0, n - 1, k);
//        if (hi == -1)
//        {
//            return 0;
//        }
//        int lo = getLeastMatchingIndex (s, 0, n - 1, k);
//        return hi - lo + 1;
        return 0;
    }

//    public String getKthSortedSuffix(int k)
//    {
////        return ss.substring (suffix[k].idx);
//        re
//    }

    public int getMaxMatchingIndex(char[] s, int lo, int hi, int len)
    {
        if (lo > hi)
        {
            return -1;
        }
        int mid = (lo + hi) >> 1;
        int cmp = compare (s, 0, len - 1, suffix[mid].idx, Math.min (suffix[mid].idx + len-1, n - 1), str);
//        System.out.println (cmp);
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
//        for (int i = lo1; i <= hi1 ; i++)
//        {
//            System.out.print (s1[i]);
//        }
//        System.out.println ();
//        for (int i = lo2; i <= hi2 ; i++)
//        {
//            System.out.print (s2[i]);
//        }
//        System.out.println ();
        for (i1 = lo1, i2 = lo2; i1 <= hi1 && i2 <= hi2; i1++, i2++)
        {
            int z = s1[i1] - s2[i2];
            if (z != 0)
            {
                return z;
            }
        }
//        System.out.println (i1 + " " + hi1 + " " + i2 + " " + hi2);
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
        int cmp = compare (s, 0, len - 1, suffix[mid].idx, Math.min (suffix[mid].idx + len - 1, n - 1), str);
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

    public class Suffix implements Comparable<Suffix>
    {
        int idx;
        int firstChar;

        public boolean lessThan(Suffix o)
        {
            if (gap == 0)    //sort by first character
            {
                return this.firstChar < o.firstChar;
            }
            int pos1 = rank[lvl-1][idx];
            int pos2 = rank[lvl-1][o.idx];
            if (pos1 != pos2)
            {
                return pos1 < pos2;
            }
            //else first gap characters are same in both
            int suff1 = idx + gap;
            int suff2 = o.idx + gap;
            if (suff1 >= n || suff2 >= n)
            {
                return idx > o.idx;
            }
            pos1 = rank[lvl-1][suff1];
            pos2 = rank[lvl-1][suff2];
            return pos1 < pos2;

        }
        @Override
        public int compareTo(Suffix o)
        {
            int cmp;
            if (this.lessThan (o))
            {
                return -1;
            }
            else if (o.lessThan (this))
            {
                return 1;
            }
            else
                return 0;

        }
    }

    public int n;
    public Suffix[] suffix;
    public int rank[][]; //rank[i] = rank of ith suffix = no of suffices < ith suffix
    public SuffixArray(char[] s)
    {
        str = s;
        n = s.length ;
        int lvls = (int)Math.ceil (Math.log (n)/Math.log (2.0)+1);
        suffix = new Suffix[n];
        rank = new int[lvls][n];
        for (int i = 0; i < lvls; i++)
        {
            Arrays.fill (rank[i], -1);
        }
        for (int i = 0; i < n; i++)
        {
            suffix[i] = new Suffix ();
            suffix[i].idx = i;
            suffix[i].firstChar = s[i];
        }
    }
    public int lvl;
    public int gap;
    public int[] getSuffixArray()
    {
        //sort suffices by first character
        Arrays.sort (suffix);
        updateRanks ();
//        for (int i = 0; i < n; i++)
//        {
//            System.out.println (suffix[i].idx+" "+ ss.substring (suffix[i].idx) + " "+ rank[suffix[i].idx]);
//        }
//        System.out.println ("----------");
        for (gap = 1, lvl = 1; gap * 2 <= n; gap += gap, lvl++)    //check exit condition
        {
            Arrays.sort (suffix);
            //now has 2*gap same or 1<<lvl same

//            System.out.println ("----------");
            //now the suffices are sorted based on first gap characters
            updateRanks ();
//            for (int i = 0; i < n; i++)
//            {
//                System.out.println (suffix[i].idx+" "+ ss.substring (suffix[i].idx) + " "+ rank[suffix[i].idx]);
//            }
//            System.out.println ("----------");

            if (rank[lvl][suffix[n - 1].idx] == n - 1)
            {
                break;
            }
        }
        int SA[] = new int[n];
        for (int i = 0; i < n; i++)
        {
            SA[i] = suffix[i].idx;
        }
        return SA;
    }

    private void updateRanks()
    {
        rank[lvl][suffix[0].idx] = 0;
        for (int i = 1; i < n; i++)
        {
            rank[lvl][suffix[i].idx] = rank[lvl][suffix[i - 1].idx] + ((suffix[i].compareTo (suffix[i-1])==0) ? 0 : 1);
        }
    }



    //get length of lcp for xth and yth suffix
    //if rank[lvl][x] == rank[lvl][y]
    //has 2^lvl same characters
    public int lcp(int x, int y)
    {
        int ans = 0;
        for (int k = lvl - 1; k >= 0 && x < n && y < n; k--)
        {
            if (rank[k][x] == rank[k][y] && rank[k][x] != -1)
            {
                int same = 1 << k;
                ans += same;
                x += same;
                y += same;
            }
        }
        return ans;
    }

    //understood
    public int[] getLcpArray()
    {
        int lcp[] = new int[n];
        lcp[0] = 0;
        for (int i = 1; i < n; i++)
        {
            lcp[i] = lcp (suffix[i-1].idx, suffix[i].idx);
        }
        return lcp;
    }
    public static void main(String[] args)
    {
//        SuffixArray s = new SuffixArray ("ababa");
//        System.out.println (Arrays.toString (s.getSuffixArray ()));
//        System.out.println (Arrays.toString (s.getLcpArray ()));
//
//        System.out.println (s.getKthSortedSuffix (4));
//        System.out.println (s.getMaxMatchingIndex ("ba", 0, s.n - 1, 2));
//        System.out.println (s.getLeastMatchingIndex ("ba", 0, s.n - 1, 2));
//        System.out.println (s.countSubstring ("ba"));

    }
}
