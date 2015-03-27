package Interviews;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by ksb on 28-06-2014.
 */
public class SuffixArrayConstruction
{

    int n;
    char s[];
    int rank[];
    Integer sorted[];
    int len;
    Comparator<Integer> x = new Comparator<Integer> ()
    {
        @Override
        public int compare(Integer i, Integer j)
        {
            if(len == 1)
            {
                return Character.compare (s[i], s[j]);
            }
            if(rank[i] < rank[j])
            {
                return  -1;
            }
            else if(rank[i] > rank[j])
            {
                return 1;
            }
            //first len/2 characters are same
            int nexti = i + len/2;
            int nextj = j + len/2;
            if(nexti >= n && nextj >=n)
            {
                return 0;
            }
            if(nexti >= n)
            {
                return -1;
            }
            if(nextj >= n)
            {
                return 1;
            }

            return Integer.compare (rank[nexti], rank[nextj]);
        }
    };
    public Integer[] suffixArray(char[] s)
    {
        this.s = s;
        n = s.length;
        rank = new int[n];
        sorted = new Integer[n];
        for (int i = 0; i < n; i++)
        {
            sorted[i] = new Integer (i);
        }
        String ss = new String (s);
        System.out.println (ss);
        for (len = 1; len < 2 * n; len *= 2)
        {
            //sort suffixes based on first len characters
            Arrays.sort (sorted, x);
            int rnk = 0;
            int newRank[] = new int[n];
            for (int i = 0; i < n-1; i++)
            {
                newRank[sorted[i]] = rnk;
                if (x.compare (sorted[i], sorted[i + 1]) != 0)
                {
                    rnk++;
                }
            }

            newRank[sorted[n-1]] = rnk;
            rank = newRank;
            System.out.println (len);
            for (int i = 0; i < n; i++)
            {
                System.out.println (ss.substring (sorted[i]) + " " + rank[sorted[i]]);
            }
        }
        return sorted;
    }

    public static void main(String[] args)
    {
        String ss ="banana";
        char s[] = ss.toCharArray ();
        SuffixArrayConstruction SA = new SuffixArrayConstruction ();
        System.out.println (Arrays.toString (SA.suffixArray (s)));

    }
}
