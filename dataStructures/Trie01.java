package dataStructures;

/**
 * Created by ksb on 5/2/14.
 */
public class Trie01
{
    //note structure is similar to heap
    //its a 0 - 1 trie. i.e trie for storing bits
    //left child is 0, right child is 1
    //indexing from 0, therefore left child is 2*lvl+1, right is 2*lvl+2
    //tree[i] stores no of nodes passing through that level
    //implement query on your own

    int maxB;
    int levels;
    int tree[];

    public Trie01(int maxBits)
    {
        maxB = maxBits;
        levels = maxB + 1;
        tree = new int[1 << (levels + 1) - 1];
    }

    public char[] getBits(int i)
    {
        String s = Integer.toBinaryString (i);
        int n = s.length ();
        StringBuilder zero = new StringBuilder ();
        while (n + zero.length () != maxB)
        {
            zero.append ("0");
        }
        zero = zero.append (s);
        return zero.toString ().toCharArray ();
    }

    public void insert(char[] a)
    {
        int cnt = 0;
        int lvl = 0;
        while (cnt < a.length)
        {
            tree[lvl]++;
            lvl = (a[cnt] == '0') ? (2 * lvl + 1) : (2 * lvl + 2);
            cnt++;
        }
        tree[lvl]++;
    }
}
