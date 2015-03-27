package Interviews;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 30/6/13
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongestSubstringWORepaatingChars
{
    /*
    cleared by LeetCode
     */
    public static int lengthOfLongestSubstring(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        char a[] = s.toCharArray ();
        HashSet<Character> set = new HashSet<Character> ();
        int i = 0;
//        set.add (a[0]);
        int maxLen = 0;
        for (int j = 0; j<a.length; )
        {
            if (set.contains (a[j]))
            {

                //i points to first occurence of a[j] + 1
                for(; true; i++)
                {
                    if (a[i] == a[j])
                    {
                        i++;
                        break;
                    }
                    set.remove (a[i]);
                }
                j++;
            }
            else
            {
                set.add (a[j]);
                int len = j-i+1;
                maxLen = Math.max (maxLen, len);
                j++;
            }
        }
        return maxLen;
    }

    public static void main(String[] args)
    {
        System.out.println (lengthOfLongestSubstring (""));
    }
}
