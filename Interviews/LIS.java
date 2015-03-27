package Interviews;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 30/6/13
 * Time: 3:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class LIS
{
    public static int lisLength(int a[])
    {
        int n = a.length;
        int lowest[] = new int[n + 1];
        //lowest[j] = min value which terminates an increasing subsequence of length j
        Arrays.fill (lowest, Integer.MAX_VALUE);
        lowest[1] = a[0];
        int maxLisLength = 1;
        for (int i = 1; i < n; i++)
        {
            //find largest j such that lowest[j] < a[i]
            //lowest[j] is also sorted since lowest[j] will be appended to a subsequence of length j-1
            //say ending at k and therefore k<lowest[j]
            //and lowest[j-1]<=k, hence lowest[j-1] <=k < lowest[j]
            //hence lowest[j-1]<lowest[j]
            //hence we can binary search over lowest[j] to find max. predecessor of a[i]
            int j = findMaxPredecessor (lowest, 1, n , a[i]);      //note lowest is 1 based array therefore [1,i]
//            System.out.println ("->"+a[i]+" "+j);
            maxLisLength = Math.max (j + 1, maxLisLength);
            //new lisLength is of length j+1
            lowest[j + 1] = Math.min (lowest[j + 1], a[i]);
        }
        return maxLisLength;
    }

    //return 0 if no predecessor found
    //returns max index of a "strict" predecessor
    private static int findMaxPredecessor(int[] a, int lo, int hi, int key)
    {

        if (lo > hi)
        {
            return 0;
        }
        int mid = lo + (hi - lo) / 2;
        if (a[mid] < key)
        {
            //a[mid] is predecessor
            return Math.max (mid, findMaxPredecessor (a, mid + 1, hi, key));
        }
        else if (a[mid] > key)
        {
            return findMaxPredecessor (a, lo, mid - 1, key);
        }
        else   //a[mid]==key therefore mid-1 is max index of a predecessor, since a[mid-1] != key since sorted
        //in stricly inc order
        {
            return mid-1;
        }
    }

    //returns lis
    public static Stack<Integer> lis2(int a[])
    {
        int n = a.length;
        int lowest[] = new int[n + 1];
        int index[] = new int[n+1]; //index j is index of the corresponding lowest element
        int pred[] = new int[n]; //pred[i] is index predecessor of a[i] in lis ending at a[i]
        //lowest[j] = min value which terminates an increasing subsequence of length j
        Arrays.fill (lowest, Integer.MAX_VALUE);
        Arrays.fill (index, -1);
        Arrays.fill (pred, -1);
        index[1] = 0;
        lowest[1] = a[0];
        pred[0] = -1;
        int maxLisLength = 1;
        for (int i = 1; i < n; i++)
        {
            //find largest j such that lowest[j] < a[i]
            //lowest[j] is also sorted since lowest[j] will be appended to a subsequence of length j-1
            //say ending at k and therefore k<lowest[j]
            //and lowest[j-1]<=k, hence lowest[j-1] <=k < lowest[j]
            //hence lowest[j-1]<lowest[j]
            //hence we can binary search over lowest[j] to find max. predecessor of a[i]
            int j = findMaxPredecessor (lowest, 1, n , a[i]);      //note lowest is 1 based array therefore [1,i]
//            System.out.println ("->"+a[i]+" "+j);

            maxLisLength = Math.max (j + 1, maxLisLength);
            //new lisLength is of length j+1
            if (lowest[j + 1] > a[i])
            {
                lowest[j + 1] = a[i];
                index[j + 1] = i;
                pred[i] = index[j];
            }
        }
        Stack<Integer> stack = new Stack<Integer> ();
        int cur = index[maxLisLength];
        while (cur!=-1)
        {
            stack.push (a[cur]);
            cur = pred[cur];
        }
        return stack;
    }

    public static void main(String[] args)
    {
        int A[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
        System.out.println ("hello");
        Stack<Integer> stack = lis2 (A);
        while (!stack.empty ())
        {
            System.out.print (stack.pop () + " ");
        }
    }


    public static int equalLisLength(int[] a)
    {
        int n = a.length;
        int lowest[] = new int[n + 1];
        //lowest[j] = min value which terminates an increasing subsequence of length j
        Arrays.fill (lowest, Integer.MAX_VALUE);
        lowest[1] = a[0];
        int maxLisLength = 1;
        for (int i = 1; i < n; i++)
        {
            int j = findMaxEqualPredecessor (lowest, 1, n, a[i]);
            maxLisLength = Math.max (j + 1, maxLisLength);
            lowest[j + 1] = Math.min (lowest[j + 1], a[i]);
        }
        return maxLisLength;
    }

    private static int findMaxEqualPredecessor(int[] a, int lo, int hi, int key)
    {
        if (lo > hi)
        {
            return 0;
        }
        int mid = lo + (hi - lo) / 2;
        if (a[mid] <= key)
        {


            //a[mid] is predecessor
            return Math.max (mid, findMaxEqualPredecessor (a, mid + 1, hi, key));
        }
        else
        {
            return findMaxEqualPredecessor  (a, lo, mid - 1, key);
        }
    }
}
