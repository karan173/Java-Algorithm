package Interviews;

import dataStructures.minMaxQueue;

/**
 * Created by ksb on 04-09-2014.
 */
//This is O(1) amortized push queue, like sliding window

class Solver
{
    int a[];
    public Solver(int[] a)
    {
        this.a = a;
    }

    public void solve()
    {
        int ans[] = new TwoMinMax().twominmax (a);
        for (int i = ans[0]; i < ans[1]; i++)
        {
            System.out.print (a[i] + " ");
        }
        System.out.println ();
    }
}
public class TwoMinMax
{
    //returns length 2 array containing optimal [left, right]
    int[] twominmax(int a[])
    {
        if (a.length == 0)
        {
            return null;
        }
        int n = a.length;
        minMaxQueue queue = new minMaxQueue ();
        int left = 0;
        int right = 0;
        int bestLeft = 0, bestRight = 0;
        //invariant we have elements from [left, right)
        while (left < n)
        {
            while (right < n && canBeAdded (a[right], queue))
            {
                queue.enqueue (a[right++]);
            }
            int bestSize = bestRight - bestLeft;
            int currentSize = right - left;
            if (currentSize > bestSize)
            {
                bestLeft = left;
                bestRight = right;
            }
            queue.remove ();
            left++;
        }
        int ans[] = {bestLeft, bestRight};
        return ans;
    }
    private boolean canBeAdded(int x, minMaxQueue queue)
    {
        if (queue.isEmpty ())
        {
            return true;
        }
        int currentMin = queue.getMin ();
        int currentMax = queue.getMax ();
        int newMin = Math.min (currentMin, x);
        int newMax = Math.max (currentMax, x);
        return newMin * 2 > newMax;
    }
    public static void main(String[] args){
        new Solver(new int[] {4, 5, 100, 9, 10, 11, 12, 15, 200}).solve();
        new Solver(new int[] {4, 7, 5, 6}).solve();
        new Solver(new int[] {20, 7, 5, 6}).solve();
        new Solver(new int[] {20, 4, 1, 3}).solve();
        new Solver(new int[] {4, 5, 9, 10, 11, 12, 17, 20}).solve();
        new Solver(new int[] {4, 5, 100, 9, 10, 11, 12, 15, 101,102,103,104,105,106,107,108,109,110,111,112}).solve();
        new Solver(new int[] {9, 5, 10, 11, 12, 15, 100}).solve();
        new Solver(new int[] {201,100,9,101,11,12,15,4,5,200}).solve();
        new Solver(new int[] {201,4,5,100,9,101,11,12,15,200}).solve();
        new Solver(new int[] {1,1,1,1,1,1,1,1,101,200,1}).solve();
    }

}
