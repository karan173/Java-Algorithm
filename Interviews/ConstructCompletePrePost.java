package Interviews;

/**
 * Created by ksb on 05-07-2014.
 */
public class ConstructCompletePrePost
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

    int n, preIndex;
    TreeNode construct(int pre[], int post[])
    {
        n = post.length;
        preIndex = 0;
        return constructUtil (pre, post, 0, n - 1);
    }

    //we construct tree in preorder fashion - NLR
    private TreeNode constructUtil(int[] pre, int[] post, int lo, int hi)
    {
        if (preIndex >= n || lo > hi)
        {
            return null;
        }
        TreeNode root = new TreeNode (pre[preIndex++]);
        if (preIndex >= n)
        {
            return root;
        }
        //post is LRN, means all children of the node are to its left
        //post[lo, hi] is the postorder for this tree
        int indexLeft = -1;
        for (int i = lo; i <= hi; i++)
        {
            if (post[i] == pre[preIndex])
            {
                indexLeft = i;
                break;
            }
        }
        if (indexLeft != -1)  //since complete, also doesnt have a right child
        {
            root.left = constructUtil (pre, post, lo, indexLeft);
            //now left tree is constructed
            //preIndex now points to right node
            root.right = constructUtil (pre, post, indexLeft + 1, hi - 1);
        }
        return root;
    }

    public static void main(String[] args)
    {
        int pre[] = {1, 2, 4, 8, 9, 5, 3, 6, 7};
        int post[] = {8, 9, 4, 5, 2, 6, 7, 3, 1};
        TreeNode root = new ConstructCompletePrePost ().construct (pre, post);
        root.print ();
    }
}
