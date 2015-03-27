package Interviews;

import java.util.*;
/**
 * Created by ksb on 07-09-2014.
 */
public class MergeBST2
{
    static class TreeNode
    {
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode(int val)
        {
            this.val = val;
        }

        @Override
        public String toString()
        {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }

    static class InorderIterative
    {
        Stack<TreeNode> stack;
        TreeNode curr, prev;

        InorderIterative(TreeNode root)
        {

            stack = new Stack<TreeNode> ();
            if (root == null)
            {
                return;
            }
            prev = null;
            stack.push (root);
            advanceState ();
        }

        private void advanceState()
        {
            while (!stack.isEmpty ())
            {
                TreeNode curr = stack.peek ();
                //going down the tree
                if (prev == null || prev.left == curr || prev.right == curr)
                {
                    if (curr.left != null)
                    {
                        prev = curr;
                        stack.push (curr.left);
                    }
                    else
                    {
                        return;
                    }
                }
                else if (curr.left == prev) //going up from left
                {
                    return;
                }
                else //going up from right
                {
                    prev = curr;
                    stack.pop ();
                }
            }
        }



        TreeNode peekNextInorder()
        {
            return stack.isEmpty () ? null : stack.peek ();
        }

        TreeNode popNextInorder()
        {
            if (stack.isEmpty ())
            {
                return null;
            }
            TreeNode curr = stack.peek ();
            if (curr.right != null)
            {
                stack.push (curr.right);
            }
            else
            {
                stack.pop ();
            }
            prev = curr;
            advanceState ();
            return curr;
        }
    }

    public static void main(String[] args)
    {
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.left.right = new TreeNode (2);

//        TreeNode root2 = new TreeNode(4);
//        root2.left = new TreeNode(2);
//        root2.right = new TreeNode(6);
//        root2.right.right = new TreeNode (8);
//        root2.right.left = new TreeNode (5);
        TreeNode root2 = null;
        MergeBST2 mergeBST2 = new MergeBST2 ();
        InorderIterative it1 = new InorderIterative (root1);
        InorderIterative it2 = new InorderIterative (root2);
        while (true)
        {
            TreeNode t1 = it1.peekNextInorder ();
            TreeNode t2 = it2.peekNextInorder ();
            if (t1 == null && t2 == null)
            {
                break;
            }
            if (t2 == null)
            {
                System.out.println (it1.popNextInorder ().val);
            }
            else if (t1 == null)
            {
                System.out.println (it2.popNextInorder ().val);
            }
            else
            {
                if (t1.val < t2.val)
                {
                    System.out.println (it1.popNextInorder ().val);
                }
                else if (t1.val > t2.val)
                {
                    System.out.println (it2.popNextInorder ().val);
                }
                else
                {
                    System.out.println (it1.popNextInorder ().val);
                    System.out.println (it2.popNextInorder ().val);
                }
            }
//            System.out.println (it1.stack.toString ());
        }

    }
}
