package Interviews;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by ksb on 05-07-2014.
 */
//O(n) method, gfg at best has O(n^2)
public class ConstructInorderLevel
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


    private class Info
    {
        int lo, hi;
        TreeNode par;
        boolean isLeft = false;
        private Info(int lo, int hi, TreeNode node, boolean isLeft)
        {
            this.lo = lo;
            this.hi = hi;
            this.par = node;
            this.isLeft = isLeft;
        }
    }
    TreeNode construct(int level[], int inorder[])
    {
        int n = inorder.length;
        int levelIndex = 0;
        //we construct tree in level order fashion
        Info s = new Info (0, n - 1, null, false);
        Queue<Info> queue = new ArrayDeque<Info> ();
        queue.add (s);
        TreeNode root = null;
        while (levelIndex < n)
        {
            Info p = queue.remove ();
            int current = level[levelIndex++];
            int inorderIndex = -1;
            for (int i = p.lo; i <= p.hi; i++)
            {
                if (inorder[i] == current)
                {
                    inorderIndex = i;
                    break;
                }
            }
            TreeNode curr = new TreeNode (current);
            if (p.par == null)
            {
                root = curr;
            }
            else if (p.isLeft)
            {
                p.par.left = curr;
            }
            else
            {
                p.par.right = curr;
            }
            if (p.lo <= inorderIndex - 1)
            {
                queue.add (new Info (p.lo, inorderIndex - 1, curr, true));
            }
            if (inorderIndex + 1 <= p.hi)
            {
                queue.add (new Info (inorderIndex + 1, p.hi, curr, false));
            }
        }
        return root;
    }

    public static void main(String[] args)
    {
        int in[]    = {4, 8, 10, 12, 14, 20, 22};
        int level[] = {20, 8, 22, 4, 12, 10, 14};
        new ConstructInorderLevel ().construct (level, in).print ();
    }


}
