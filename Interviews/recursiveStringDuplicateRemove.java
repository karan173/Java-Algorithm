package Interviews;

import java.util.*;
/**
 * Created by ksb on 06-09-2014.
 */
public class recursiveStringDuplicateRemove
{

    String func(String s)
    {
        Stack<Character> stack =  new Stack<Character>();
        char lastDeleted = '$';
        for (char c : s.toCharArray ())
        {
            if (stack.isEmpty () || (stack.peek () != c && lastDeleted != c))
            {
                stack.push (c);
                lastDeleted = '$';
            }
            else
            {
                while (!stack.isEmpty () && stack.peek () == c)
                {
                    stack.pop ();
                }
                lastDeleted = c;
            }
        }
        return stack.toString ();
    }

    public static void main(String[] args)
    {
        recursiveStringDuplicateRemove x = new recursiveStringDuplicateRemove ();

        System.out.println (x.func ("geeksforgeeg"));
        System.out.println (x.func ("azxxzy"));
        System.out.println (x.func ("caaabbbaacdddd"));
        System.out.println (x.func ("gghhg"));
        System.out.println (x.func ("aaaacddddcappp"));
        System.out.println (x.func ("aaaaaaaaaa"));
        System.out.println (x.func ("qpaaaaadaaaaadprq"));
        System.out.println (x.func ("acaaabbbacdddd"));
        System.out.println (x.func ("acbbcddc"));
    }

}
