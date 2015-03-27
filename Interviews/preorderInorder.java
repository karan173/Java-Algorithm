package Interviews;

/**
 * Created by ksb on 12-05-2014.
 */


//O(N*N) solution
//http://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
//http://oj.leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
//Note this is Binary tree not BST, therefore can't do binary search in inorder, hence O(N*N)
//if BST nlogn
class Solution
{
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    int n;
    int preCtr = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder)
    {
        n = inorder.length;
        return getTree(preorder, inorder, 0, n - 1);
    }
    //we are doing a preorder traversal in buildtree
    public TreeNode getTree(int[] preorder, int[] inorder, int lo, int hi)
    {
        if(lo > hi)
        {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preCtr++]);
        int idx = -1;
        for (int i = 0; i < n; i++)
        {
            if (root.val == inorder[i])
            {
                idx = i;
                break;
            }
        }
        root.left = getTree(preorder, inorder, lo, idx-1);
        root.right = getTree(preorder, inorder, idx+1, hi);
        return root;
    }
    public static void main(String[] args)
    {
        Solution x = new Solution ();
        int preorder[] = {1,2};
        int inorder[] = {2,1};
        TreeNode root = x.buildTree (preorder, inorder);

    }

}
