package Interviews;

import java.util.*;
/**
 * Created by ksb on 07-09-2014.
 */
public class constructPrePost2
{
    private class TreeNode
    {
        TreeNode left, right;
        int val;

        private TreeNode(int val)
        {
            this.val = val;
        }

        public void print()
        {
            if (left != null)
            {
                left.print ();
            }
            System.out.println (val);
            if (right != null)
            {
                right.print ();
            }
        }
    }

    TreeNode construct(int pre[], int post[])
    {
        ArrayList<Integer> revPost = new ArrayList<Integer> ();
        for (int x : post)
        {
            revPost.add (x);
        }
        Collections.reverse (revPost);
        for (int i = 0; i < post.length; i++)
        {
            post[i] = revPost.get (i);
        }
        int n = post.length;
        return constructUtil (pre, post, 0, n - 1, 0, n - 1);
    }

    private TreeNode constructUtil(int[] nlr, int[] nrl, int lo1, int hi1, int lo2, int hi2)
    {
        int n1 = hi1 - lo1 + 1;
        int n2 = hi2 - lo2 + 1;
        if (n1 != n2)
        {
            throw new RuntimeException ();
        }
        if (n1 < 0)
        {
            return null;
        }
        if (nlr[lo1] != nrl[lo2])
        {
            throw new RuntimeException ();
        }
        TreeNode root = new TreeNode (nlr[lo1]);
        if (n1 == 1)
        {
            return root;
        }
        int leftVal = nlr[lo1 + 1];
        int rightVal = nrl[lo2 + 1];
        int idx1 = getIndex (nlr, rightVal);
        int idx2 = getIndex (nrl, leftVal);
        root.left = constructUtil (nlr, nrl, lo1 + 1, idx1 - 1, idx2, hi2);
        root.right = constructUtil (nlr, nrl, idx1, hi1, lo2 + 1, idx2 - 1);
        return root;
    }

    private int getIndex(int[] nlr, int leftVal)
    {
        for (int i = 0; i < nlr.length; i++)
        {
            if (nlr[i] == leftVal)
            {
                return i;
            }
        }
        throw new RuntimeException ();
    }

    public static void main(String[] args)
    {
        int pre[] = {1, 2, 4, 8, 9, 5, 3, 6, 7};
        int post[] = {8, 9, 4, 5, 2, 6, 7, 3, 1};
        TreeNode root = new constructPrePost2 ().construct (pre, post);
        root.print ();
    }
}
