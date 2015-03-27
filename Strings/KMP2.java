package Strings;

import java.util.Arrays;

/**
 * Created by ksb on 04-08-2014.
 */
//based on http://www.keithschwarz.com/interesting/code/?dir=knuth-morris-pratt
public class KMP2
{
    /*
    Complexity analysis
    prefixFunction

    Total m-1 terminal (ending) iterations of getPrefixLength
    Also in each non terminal iteration, value of prefix[i] decreases by atleast 1.
    But prefix>=0, hence total decrease = total possible increase
    Increase is at most 1 in each iteration.
    Therefore net decrease = total max increase = m
    therefore total O(m) non terminal iterations of getPrefixLength
    Complexity = O(m)
    See http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm
     */
    public static int[] getPrefixFunction(char pat[])
    {
        int n = pat.length;
        int prefix[] = new int[n];
        prefix[0] = 0;
        //prefix[i] = border of s[0..i] = length of longest proper prefix of s[0...i] which is also its suffix
        for (int i = 1; i < n; i++)
        {
            prefix[i] = getPrefixLength (pat, prefix, i, pat[i]);
        }
        return prefix;
    }

    /*
    Returns longest border of pat[0...n-1] when extended by character c, extend border of first n characters by c
     */
    public static int getPrefixLength(char[] pat, int[] prefix, int n, char c)
    {
        if (n == 0) //empty string + c = c [1 character string has no border]
        {
            return 0;
        }
        int k = prefix[n - 1];
        if (pat[k] == c)
        {
            return k + 1;
        }
        return getPrefixLength (pat, prefix, k, c);
    }

    /*
    Complexity consider textCtr+matched

    On a match, matched increases, hence textCtr+matched increases.
    Hence a matched text character is never compared again.
    Therefore maximum increase in matched = O(n)

    On a mismatch ,  textCtr + matched remains same, but matched decreases by matched - prefix[matched-1]
    It decreases by atleast 1, also matched >=0
    amortized total decrease in matched = total increase = O(n)

    Hence complexity = O(n) amortized, 2*n steps

    To see why incrementing i works, ask yourself the question
    What is the longest prefix of pat[0....matched-1] which is a suffix of s[i...i+matched-1]
    OR
    What is the longest prefix of pat[0....matched-1] which is a suffix of p[0....matched-1]
     */
    public static boolean []kmp(char s[], char pat[])
    {
        int n = s.length;
        int m = pat.length;
        boolean match[] = new boolean[n];
        Arrays.fill (match, false);
        int prefix[] = getPrefixFunction (pat);
        int textCtr = 0;
        int matched = 0;
        while (textCtr + m <= n)
        {
//            System.out.println (textCtr + " " + matched);
            if(s[textCtr + matched] == pat[matched])
            {
                matched++;
                if(matched == m)
                {
                    match[textCtr] = true;
                    textCtr += matched - prefix[matched - 1];
                    matched = prefix[matched - 1];
                }
                continue;
            }
            //no match
            //reposition textCtr
            if(matched == 0)
            {
                textCtr++;
            }
            else
            {
                textCtr += matched - prefix[matched - 1];
                matched = prefix[matched - 1];
            }
        }
        return match;
    }
    public static void main(String[] args)
    {
        char pat[] = new String ("abc").toCharArray ();
        char text[] = new String ("ababcxaabc").toCharArray ();
        System.out.println (Arrays.toString(kmp (text, pat)) );
    }
}
