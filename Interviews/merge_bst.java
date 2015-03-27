package Interviews;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ksb on 29-06-2014.
 */
//http://www.geeksforgeeks.org/merge-two-bsts-with-limited-extra-space/


public class merge_bst
{
    static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    class Pair
    {
        TreeNode prev, ret;
    }

    //should be called whenever a new TreeNode is needed
    void doInorder(Stack<TreeNode> stack, Pair p)
    {
        p.ret = null;
        while (!stack.empty ())
        {
            TreeNode curr = stack.peek ();
            //going down
            if (p.prev == null || p.prev.right == curr || p.prev.left == curr)
            {
                if (curr.left != null)
                {
                    stack.push (curr.left);
                }
                else if (curr.right != null)
                {
                    p.ret = curr;
                    stack.push (curr.right);
                }
                else
                {
                    p.ret = curr;
                    stack.pop ();
                }
            }
            else if (curr.left == p.prev) //going up from left
            {
                if (curr.right != null)
                {
                    p.ret = curr;
                    stack.push (curr.right);
                }
                else
                {
                    p.ret = curr;
                    stack.pop ();
                }
            }
            else //going up from right
            {
                stack.pop ();
            }
            p.prev = curr;
            if (p.ret != null) //a new TreeNode was produced
            {
                return;
            }
        }
    }


    
    ArrayList<Integer> merge(TreeNode root1, TreeNode root2)
    {
        ArrayList<Integer> ans = new ArrayList<Integer> ();
        //we have to do inorder traversal of both nodes
        Stack<TreeNode> stack1 = new Stack<TreeNode> ();
        Stack<TreeNode> stack2 = new Stack<TreeNode> ();
        Pair p1 = new Pair ();
        Pair p2 = new Pair ();
        if (root1 != null)
        {
            stack1.push (root1);
        }
        if (root2 != null)
        {
            stack2.push (root2);
        }
        while (true)
        {
            if (p1.ret == null)
            {
                doInorder (stack1, p1);
            }
            if (p2.ret == null)
            {
                doInorder (stack2, p2);
            }
            if (p1.ret == null || p2.ret == null)
            {
                break;
            }
            if (p1.ret.val <= p2.ret.val)
            {
                ans.add (p1.ret.val);
                p1.ret = null;
            }
            else
            {
                ans.add (p2.ret.val);
                p2.ret = null;
            }
        }
        while (true)    //do p1 remaining
        {
            if (p1.ret == null)
            {
                doInorder (stack1, p1);
            }
            if (p1.ret == null)
            {
                break;
            }
            else
            {
                ans.add (p1.ret.val);
                p1.ret = null;
            }
        }

        while (true)    //do p1 remaining
        {
            if (p2.ret == null)
            {
                doInorder (stack2, p2);
            }
            if (p2.ret == null)
            {
                break;
            }
            else
            {
                ans.add (p2.ret.val);
                p2.ret = null;
            }
        }
        return ans;

    }

    public static void main(String[] args)
    {
         /* Let us create the following tree as first tree
            3
          /  \
         1    5
     */
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(5);
        root1.right.right = new TreeNode (6);
        root1.right.right.right = new TreeNode (7);

//        TreeNode root2 = new TreeNode(4);
//        root2.left = new TreeNode(2);
//        root2.right = new TreeNode(6);
//        root2.right.right = new TreeNode (8);
//        root2.right.left = new TreeNode (5);
        TreeNode root2 = null;
        merge_bst m = new merge_bst ();
        System.out.println (m.merge (root1, root2));
    }


}
