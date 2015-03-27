package Strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ksb on 29-08-2014.
 */
public class DFA
{
    /*
    Class for string matching using DFA
    Complexity is O(n + m*r)
    where m = pattern length
    n = text length
    r = character set size/ max no of distinct chars in pattern
     */
    boolean[] match(char text[], char pattern[])
    {
        int n = text.length;
        int m = pattern.length;
        boolean match[] = new boolean[n];
        Arrays.fill (match, false);
        if (m == 0)
        {
            Arrays.fill (match, true);
            return match;
        }
        if (n == 0)
        {
            return match;
        }
        HashMap<Character, Integer> map = getCharacterMap(pattern);
        int dfa[][] = getDFA (pattern, map);
        int state = 0;
        for (int i = 0; i < n; i++)
        {
            if(!map.containsKey (text[i]))
            {
                state = 0;
                continue;
            }
            state = dfa[state][map.get (text[i])];
            if (state == m)   //m characters have matched
            {
                match[i - m + 1] = true;
                state = dfa[state][0];
            }
        }
        return match;
    }

    /*
    just map each character in pattern to a int
     */
    private HashMap<Character, Integer> getCharacterMap(char[] pattern)
    {
        HashMap<Character, Integer> map = new HashMap<Character, Integer> ();
        int ctr = 0;
        for(char c : pattern)
        {
            if(!map.containsKey (c))
            {
                map.put (c, ctr++);
            }
        }
        return map;
    }

    private int[][] getDFA(char[] s, HashMap<Character, Integer> map)
    {
        int n = s.length;
        int r = map.size ();
        int dfa[][] = new int[n + 1][r];
        Arrays.fill (dfa[0], 0);
        dfa[0][map.get (s[0])] = 1;
        int prev = 0;
        for (int cur = 1; cur < n; cur++)
        {
            for (int i = 0; i < r; i++)
            {
                dfa[cur][i] = dfa[prev][i];
            }
            dfa[cur][map.get (s[cur])] = cur + 1;
            prev = dfa[prev][map.get (s[cur])];
        }
        Arrays.fill (dfa[n], prev); //this is done so that after finding a match, we can backtrack to next best possible
        //state, useful for finding multiple matches
        return dfa;
    }

    public static void main(String[] args)
    {
        String text = "aaaaaaa";
        String patt = "aa";
        System.out.println (Arrays.toString (new DFA().match (text.toCharArray (), patt.toCharArray ())));
    }
}
