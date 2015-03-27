package dataStructures;

import ArrayUtils.ArrayUtils;
import InputOutput.FastReader;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 19/8/13
 * Time: 1:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class StabbingQuery
{
    /*
    package tasks;

import ArrayUtils.ArrayUtils;
import InputOutput.FastReader;
import java.io.PrintWriter;
import java.util.ArrayList;

class Interval
{
    int l, r, k;
    int cnt;
    public Interval(int ll, int rr, int kk)
    {
        l =ll;
        r=rr;
        k =kk;
    }
    @Override
    public String toString()
    {
        return "Interval{" +
                "l=" + l +
                ", r=" + r +
                ", k=" + k +
                ", cnt=" + cnt +
                '}';
    }
}
class SegNode
{
    ArrayList<Interval> list = new ArrayList<Interval> ();
    static Interval curInterval;
    //add state values here and static variables from main to assist update/initialise
    //split and merge never have null l and r


    void update()
    {
        list.add (curInterval);
    }
    void initialiseLeaf(int range){}
    public static boolean hasUpdate = true; //or has split


}
class SegTree
{
    static ArrayList<Interval> ans;
    SegNode tree[];
    //we have index from 1
    int n;

    //creates a SegTree with range [1, MAXN]


    public SegTree(int MAXN)
    {
        n = MAXN;
        int nodeCount = Integer.highestOneBit (MAXN) << 2;
        tree = new SegNode[nodeCount + 1];
        //for extra cushion one extra level is created, also indexing is done from 1
        initialiseTree (1, 1, MAXN);
    }

    private void initialiseTree(int index, int start, int end)
    {
        tree[index] = new SegNode ();
        if (start == end)
        {
            tree[index].initialiseLeaf (start);
            return;
        }
        //initialise subtrees
        int mid = (start + end) >> 1;
        int lChild = index << 1;
        int rChild = lChild + 1;
        initialiseTree (lChild, start, mid);
        initialiseTree (rChild, mid + 1, end);
    }


    //[start, end] is the query range

    public void rangeQuery(int start, int end)
    {
        rangeQuery (1, start, end, 1, n);
    }
    private void rangeQuery(int index, int start, int end, int leftRange, int rightRange)
    {
        //if within query range, return current node
        if(leftRange >= start && rightRange<=end)
        {
            SegTree.ans.addAll (tree[index].list);
            return;
        }

        //if doesnt intersect query change, return null
        if(rightRange < start || leftRange > end)
            return;

        //now current node will never be a leaf node since both intersection and not intersection cases done
        //therefore split never has null nodes
        SegTree.ans.addAll (tree[index].list);
        //else some intersection
        int lChild = index<<1;
        int rChild = lChild+1;
        int mid = (leftRange+rightRange)>>1;
        //lazily propogate state changes to children

        //perform queries on children
        rangeQuery (lChild, start, end, leftRange, mid);
        rangeQuery (rChild, start, end, mid+1, rightRange);

        //merge info from children into current node, since children are updated
        //again both not null



    }
    public void rangeUpdate(int start, int end)
    {
        rangeUpdate (1, start, end, 1, n);
    }
    private void rangeUpdate(int index, int start, int end, int leftRange, int rightRange)
    {

        //if within update range, update current node and return
        if (leftRange >= start && rightRange <= end)
        {
            tree[index].update ();
            return;
        }

        //if doesnt intersect update change, return
        if(rightRange < start || leftRange > end)
            return;

        //now current node will never be a leaf node since both intersection and not intersection cases done
        //therefore split never has null nodes

        //else update range intersects some part of current node
        int lChild = index<<1;
        int rChild = lChild+1;
        int mid = (leftRange + rightRange)>>1;
        //lazily propogate state changes to children

        //perform updates on children
        rangeUpdate (lChild, start, end, leftRange, mid);
        rangeUpdate (rChild, start, end, mid+1, rightRange);

        //merge info from children into current node, since children are updated
    }
}
public class hello {
    public void solve(int testNumber, FastReader in, PrintWriter out)
    {
        int n = in.nextInt ();
        int q = in.nextInt ();
        int a[] = new int[n];
        ArrayUtils.readIntArray (a, in);
        SegTree segTree = new SegTree (n + 5);
        Interval interval[] = new Interval[q];
        for (int i = 0; i < q; i++)
        {
            int l = in.nextInt ();
            int r = in.nextInt ();
            int k = in.nextInt ();
            interval[i] = new Interval (l, r, k);
            SegNode.curInterval = interval[i];

            segTree.rangeUpdate (l, r);
        }
        for (int i = 0; i < n; i++)
        {
            SegTree.ans = new ArrayList<Interval> ();
//            System.out.println ("i-" + i);
            segTree.rangeQuery (i + 1, i + 1);
            for (Interval z : SegTree.ans)
            {
//                out.println ((i + 1) + " " + z);
                if (a[i] % z.k == 0)
                {
                    z.cnt++;
                }
            }
        }
        for (int i = 0; i < q; i++)
        {
            out.println (interval[i].cnt);
        }
    }
}

*/
}
