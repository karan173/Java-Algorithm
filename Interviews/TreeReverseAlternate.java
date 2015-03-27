package Interviews;

/**
 * Created by ksb on 06-09-2014.
 */
public class TreeReverseAlternate
{
    static class Node
    {
        Node left, right;
        char c;

        Node(char c)
        {
            this.c = c;
        }
    }
    static void reverseAlternate(Node root)
    {

    }

    public static void main(String[] args)
    {
        Node root = new Node ('a');
        root.left = new Node ('b');
        root.right = new Node ('c');
        root.left.left = new Node ('d');
        root.left.right = new Node ('e');
        root.right.left = new Node ('f');
        root.right.right = new Node ('g');
        root.left.left.left = new Node ('h');
        root.left.left.right = new Node ('i');
        root.left.right.left = new Node ('j');
        root.left.right.right = new Node ('k');
        root.right.left.left = new Node ('l');
        root.right.left.right = new Node ('m');
        root.right.right.left = new Node ('n');
        root.right.right.right = new Node ('o');
        printInorder (root);
        System.out.println ();
        reverseAlternate (root);
        printInorder (root);
        System.out.println ();
    }

    private static void printInorder(Node root)
    {
        if (root == null)
        {
            return;
        }
        printInorder (root.left);
        System.out.print (root.c + " ");
        printInorder (root.right);
    }
}
