package Interviews;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 26/7/13
 * Time: 2:38 AM
 * To change this template use File | Settings | File Templates.
 */

public class subsets
{
    public static void main(String[] args)
    {
        int a[] = {1, 2, 3, 4};
        for (ArrayList<Integer> subset : getSubsets (a))
        {
             System.out.println (subset);
        }
        System.out.println (getSubsets (a).size ());

    }

    private static ArrayList<ArrayList<Integer>> getSubsets(int[] a)
    {
        int n = a.length;
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>> (1 << n);
        for (int i = 0; i < (1 << n) ; i++)
        {
            ans.add (getSubset (a, i));
        }
        return ans;
    }

    private static ArrayList<Integer> getSubset(int[] a, int i)
    {
        ArrayList<Integer> ans = new ArrayList<Integer> (a.length);
        for (int j = 0; (1 << (j) <= i); j++)
        {
            if (((1 << j) & i) != 0)
            {
                ans.add (a[j]);
            }
        }
        return ans;
    }

}
