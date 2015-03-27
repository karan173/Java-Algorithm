package Interviews;

/**
 * Created by ksb on 30-06-2014.
 */
public class PruneBST
{
    private static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    TreeNode pruneBST(TreeNode root, int min, int max)
    {
        if (root == null)
        {
            return null;
        }
        root.left = pruneBST (root.left, min, max);
        root.right = pruneBST (root.right, min, max);
        if (root.val >= min && root.val <= max)
        {
            return root;
        }
        //else current node is to be deleted
        //if it has both children, one of its children will also be deleted, the non deleted children should be returned
        return root.left == null ? root.right : root.left;
    }

    public static void main(String[] args)
    {
        TreeNode root = new TreeNode (6);
        root.left = new TreeNode (-13);
        root.left.right = new TreeNode (-8);
        root.right = new TreeNode (14);
        root.right.right = new TreeNode (15);
        root.right.left = new TreeNode (13);
        root.right.left.left = new TreeNode (7);
        TreeNode pruned = new PruneBST ().pruneBST (root,-13,-12);
        inorder (pruned);
    }

    private static void inorder(TreeNode root)
    {
        if (root != null)
        {
            inorder (root.left);
            System.out.println (root.val);
            inorder (root.right);
        }
    }
}
