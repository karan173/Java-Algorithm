package Interviews;

/**
 * Created with IntelliJ IDEA.
 * User: ksb
 * Date: 30/6/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class minimumJumps
{
    /*
    Tested at leetcode
     */
    public static int minJumps(int a[])
    {

        int jumps = 1; //we are starting a jump from 0
        int i = 0;     //i is current jump start position
        int maxIndex = 0;  //max index which can be reached till now
        int nextOptimalStart = 0;  //next proposed start position
        int n = a.length;
        if(n==1)
        {
            return 0;
        }
        for (int j = 0; j < n; j++)
        {
            if(j>maxIndex)             //we cant reach end of array
                return Integer.MAX_VALUE;
            int end = j + a[j];
            if (end > maxIndex)
            {
                maxIndex = end;
                nextOptimalStart = j;
            }
//            System.out.println (maxIndex  + " " + nextOptimalStart);
            if (j == i + a[i])    //we have reached end of candidates for our new start position
            {
                if (j == n - 1)  //we need not start a new jump at n-1
                {
                    break;
                }
                i = nextOptimalStart;  //start a new jump at nextOptimalStart
                jumps++;
            }
        }
        return jumps;
    }

    public static int minJumps2(int a[])
    {
        int jumps = 0;
        int maxPossible = 0;
        int maxFinished = 0;
        for (int i = 0; i < a.length; i++)
        {
            if (i > maxPossible)
            {
                return Integer.MAX_VALUE;
            }
            if (i > maxFinished)
            {
                maxFinished = maxPossible;
                jumps++;
            }
            maxPossible = Math.max (maxPossible, i + a[i]);
        }
        return jumps;
    }

    public static void main(String[] args)
    {
        int arr[] = {1, 3, 6, 3, 2, 3, 6, 8, 9, 5};
        int b[] = {3,2,1,0,5,6} ;
        int c[] = {1, 2, 3};
        int d[] ={1,2};
        System.out.println (minJumps (d));
    }
}
