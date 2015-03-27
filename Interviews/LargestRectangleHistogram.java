package Interviews;

import java.util.Stack;

/**
 * Created by ksb on 04-08-2014.
 */
public class LargestRectangleHistogram
{
    public static int [] getNextSmallest(int a[] , int n)
    {
        int next[] = new int[n];
        next[n-1] = -1;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push (n-1);
        for(int i = n-2; i>=0; i--)
        {
            while(!stack.empty() && a[stack.peek()] >= a[i])
            {
                stack.pop();
            }
            next[i] = stack.empty() ? -1 : stack.peek();
            stack.push(i);
        }
        return next;
    }

    public static int largestRectangleArea(int[] height)
    {
        //we find the area with each bar as the min height.
        //hence we must find right and left possible extent for each bar
        //left extent for a bar will go on till we find a smaller bar
        //similarly for right
        //hence we just need to find next smallest and prev smallest array for each bar
        //this can be done in O(n)
        int n = height.length;
        if(n == 0)
        {
            return 0;
        }
        int prevSmallest[] = getPrevSmallest(height, n);
        int nextSmallest[] = getNextSmallest(height, n);
        int maxArea = 0;
        for(int i = 0; i<n; i++)
        {
            int prev = prevSmallest[i];
            int next = nextSmallest[i] == -1 ? n : nextSmallest[i];
            System.out.println (prev + " " + next);
            prev++;
            next--;
            int area = height[i] * (next-prev+1);
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    public static int [] getPrevSmallest(int a[] , int n)
    {
        int prev[] = new int[n];
        prev[0] = -1;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push (0);
        for(int i = 1; i<n; i++)
        {
            while(!stack.empty() && a[stack.peek()] >= a[i])
            {
                stack.pop();
            }
            prev[i] = stack.empty() ? -1 : stack.peek();
            stack.push(i);
        }
        return prev;
    }

    public static void main(String[] args)
    {
        int h[] = {2, 4};
        System.out.println (largestRectangleArea (h));
    }
}
