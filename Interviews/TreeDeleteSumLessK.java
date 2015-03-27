package Interviews;

/**
 * Created by ksb on 30-06-2014.
 */
public class TreeDeleteSumLessK
{
    private static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    TreeNode pruneTree(TreeNode root, int k, int sumParents)
    {
        if (root == null)
        {
            return null;
        }
        sumParents += root.val;
        if (sumParents >= k)
        {
            return root;
        }
        root.left = pruneTree (root.left, k, sumParents);
        root.right = pruneTree (root.right, k, sumParents);
        if (root.left != null || root.right != null)
        {
            return root;
        }
        return null;
    }

    public static void main(String[] args)
    {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.left.left.right = new TreeNode(9);
        root.left.right.left = new TreeNode(12);
        root.right.right.left = new TreeNode(10);
        root.right.right.left.right = new TreeNode(11);
        root.left.left.right.left = new TreeNode(13);
        root.left.left.right.right = new TreeNode(14);
        root.left.left.right.right.left = new TreeNode(15);
        root = new TreeDeleteSumLessK ().pruneTree (root, 45, 0);
        inorder (root);
    }

    private static void inorder(TreeNode root)
    {
        if (root != null)
        {
            inorder (root.left);
            System.out.print  (root.val + " ");
            inorder (root.right);
        }
    }
}
