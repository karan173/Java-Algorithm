package Strings;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 26/11/13
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
/*
tested
 */
public abstract class KMP
{
    /*
    returns all occurence of pattern
     */

    public static ArrayList<Integer> kmpMatcher(char text[], char pattern[])
    {
        ArrayList<Integer> ans = new ArrayList<Integer> ();
        if (pattern.length == 0)   //empty string is found at start of text
        {
            ans.add (0);
            return ans;
        }
        int p[] = prefixFunction (pattern);
        int k = 0; //number of chars matched yet
        for (int i = 0; i < text.length; i++)
        {
            //pattern[1..k-1] is longest possible suffix of text[1..i-1]
            //see if we can extend by text[i]

            //while we dont get a match, look for largest possible extensibe suffix
            while (k > 0 && pattern[k] != text[i])
            {
                k = p[k - 1];
            }
            if (pattern[k] == text[i])
            {
                k++;
            }
            if (k == pattern.length)
            {
                ans.add (i - pattern.length + 1);
                k = p[k - 1];
            }
        }
        return ans;
    }

    public static int[] prefixFunction(char[] pattern)
    {
        int p[] = new int[pattern.length];
        //p[i] = length of longest proper prefix/suffix of pattern[i]
        p[0] = 0; //no proper suffix
        for (int i = 1; i < pattern.length; i++)
        {
            //patten[1...j-1] is a proper suffix of pattern[1...i-1]
            //if pattern[j] == pattern[i], p[i] = p[j-1]+1, extened by pattern[i]
            int k = p[i - 1];
            while (k > 0 && pattern[k] != pattern[i])
            {
                k = p[k - 1];
            }
            if (pattern[k] == pattern[i])
            {
                p[i] = k + 1;
            }
            else
            {
                p[i] = 0;
            }
        }
        return p;
    }

}
