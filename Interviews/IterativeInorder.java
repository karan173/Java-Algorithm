package Interviews;

/**
 * Created by ksb on 29-06-2014.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//THIS HAS ON DEMAND NODE GENERATION WHICH IS REQUIRED IN SOME PROBLEMS
//Tested on leetcode

class IterativeInorder
{
    static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    TreeNode prev;
    TreeNode ret;
    //should be called whenever a new node is needed
    void doInorder(Stack<TreeNode> stack)
    {
        ret = null;
        while (!stack.empty ())
        {
            TreeNode curr = stack.peek ();
            //going down
            if (prev == null || prev.right == curr || prev.left == curr)
            {
                if (curr.left != null)
                {
                    stack.push (curr.left);
                }
                else if (curr.right != null)
                {
                    ret = curr;
                    stack.push (curr.right);
                }
                else
                {
                    ret = curr;
                    stack.pop ();
                }
            }
            else if (curr.left == prev) //going up from left
            {
                if (curr.right != null)
                {
                    ret = curr;
                    stack.push (curr.right);
                }
                else
                {
                    ret = curr;
                    stack.pop ();
                }
            }
            else //going up from right
            {
                stack.pop ();
            }
            prev = curr;
            if (ret != null) //a new node was produced
            {
                return;
            }
        }
    }


    public List<Integer> inorderTraversal(TreeNode root)
    {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> list = new ArrayList<Integer> ();
        if(root != null)
            stack.push(root);

        while (true)
        {
            doInorder (stack);
            if (ret == null)
            {
               break;
            }
            list.add (ret.val);
        }
        return list;

    }

    public static void main(String[] args)
    {
        IterativeInorder inorder = new IterativeInorder ();
        TreeNode root = new TreeNode (2);
        root.left = new TreeNode (1);
        root.right = new TreeNode (4);
        System.out.println (inorder.inorderTraversal (root));

    }
}
