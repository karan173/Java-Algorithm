package dataStructures;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 21/6/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class SegTree
{
    SegNode tree[];
    //we have index from 1
    int n;
    /*
    creates a SegTree with range [1, MAXN]
     */

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
        tree[index].merge (tree[lChild], tree[rChild]);
    }

    /*
    [start, end] is the query range
     */
    public SegNode rangeQuery(int start, int end)
    {
        return rangeQuery (1, start, end, 1, n);
    }

    /*
    invariant-> any node that we travel to has already been updated
     */
    private SegNode rangeQuery(int index, int start, int end, int leftRange, int rightRange)
    {
        //if within query range, return current node
        if(leftRange >= start && rightRange<=end)
            return tree[index];

        //if doesnt intersect query change, return null
        if(rightRange < start || leftRange > end)
            return null;

        //now current node will never be a leaf node since both intersection and not intersection cases done
        //therefore split never has null nodes

        //else some intersection
        int lChild = index<<1;
        int rChild = lChild+1;
        int mid = (leftRange+rightRange)>>1;
        //lazily propogate state changes to children
        if(SegNode.hasUpdate)
            tree[index].split (tree[lChild], tree[rChild]);

        //perform queries on children
        SegNode res = new SegNode ();
        SegNode l = rangeQuery (lChild, start, end, leftRange, mid);
        SegNode r = rangeQuery (rChild, start, end, mid+1, rightRange);

        //merge info from children into current node, since children are updated
        //again both not null
        if(SegNode.hasUpdate)
            tree[index].merge (tree[lChild], tree[rChild]);

        if(l == null)
            return r;
        else if(r == null)
            return l;
        /*
        RANGE of res
        int ll = Math.max (start, leftRange);
        int rr = Math.min (end, rightRange);
        res.merge (l, r, ll ,rr , mid); mid = (leftRange+rightRange)/2
         */
        res.merge (l, r);
        return res;
    }
    public void rangeUpdate(int start, int end)
    {
        rangeUpdate (1, start, end, 1, n);
    }

    /*
    invariant-> any node that we travel to has no pending flips/updates left
     */
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
        tree[index].split (tree[lChild], tree[rChild]);

        //perform updates on children
        rangeUpdate (lChild, start, end, leftRange, mid);
        rangeUpdate (rChild, start, end, mid+1, rightRange);

        //merge info from children into current node, since children are updated
        tree[index].merge (tree[lChild], tree[rChild]);
    }
}